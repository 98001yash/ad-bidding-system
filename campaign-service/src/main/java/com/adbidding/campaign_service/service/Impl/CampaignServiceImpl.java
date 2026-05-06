package com.adbidding.campaign_service.service.Impl;

import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.enums.CampaignStatus;
import com.adbidding.campaign_service.exceptions.CampaignNotFoundException;
import com.adbidding.campaign_service.exceptions.InvalidCampaignException;
import com.adbidding.campaign_service.kafka.BidRequestProducer;
import com.adbidding.campaign_service.repository.CampaignRepository;
import com.adbidding.campaign_service.service.CampaignService;

import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.BidRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final BidRequestProducer bidRequestProducer;


    @Override
    @Transactional
    public Campaign createCampaign(Campaign campaign) {

        log.info("Creating campaign with name={}", campaign.getName());

        validateCampaign(campaign);

        campaign.setStatus(CampaignStatus.ACTIVE);

        Campaign saved = campaignRepository.save(campaign);

        log.info("Campaign created successfully with id={}", saved.getId());


        publishBidRequestEvent(saved);

        return saved;
    }


    @Override
    public Campaign getCampaignById(Long id) {

        log.info("Getting campaign with id={}", id);

        return campaignRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Campaign not found with id={}", id);
                    return new CampaignNotFoundException(id);
                });
    }

    @Override
    public List<Campaign> getActiveCampaigns() {

        LocalDateTime now = LocalDateTime.now();

        log.debug("Fetching active campaigns at time={}", now);

        return campaignRepository.findByStatusAndStartDateBeforeAndEndDateAfter(
                CampaignStatus.ACTIVE,
                now,
                now
        );
    }

    @Override
    @Transactional
    public Campaign updateCampaign(Long id, Campaign campaign) {

        log.info("Updating campaign with id={}", id);

        Campaign existing = getCampaignById(id);

        validateCampaign(campaign);

        existing.setName(campaign.getName());
        existing.setBudget(campaign.getBudget());
        existing.setStartDate(campaign.getStartDate());
        existing.setEndDate(campaign.getEndDate());
        existing.setStatus(campaign.getStatus());

        Campaign saved = campaignRepository.save(existing);

        log.info("Campaign updated successfully id={}", id);

        return saved;
    }

    @Override
    @Transactional
    public void deleteCampaign(Long id) {

        log.warn("Deleting campaign with id={}", id);

        Campaign campaign = getCampaignById(id);

        campaignRepository.delete(campaign);

        log.info("Campaign deleted successfully with id={}", id);
    }


    private void publishBidRequestEvent(Campaign campaign) {

        try {

            BidRequestEvent event = BidRequestEvent.create(
                    campaign.getId(),
                    1001L,
                    "IN",
                    "MOBILE"
            );

            log.info("Publishing BidRequestEvent requestId={}, campaignId={}",
                    event.getRequestId(),
                    event.getCampaignId());

            bidRequestProducer.publishBidRequest(event);

        } catch (Exception e) {

            log.error("Failed to publish BidRequestEvent for campaignId={}",
                    campaign.getId(),
                    e);
        }
    }


    private void validateCampaign(Campaign campaign) {

        if (campaign.getName() == null || campaign.getName().isBlank()) {
            log.error("Invalid campaign: name is missing");
            throw new InvalidCampaignException("Campaign name is required");
        }

        if (campaign.getBudget() == null || campaign.getBudget() <= 0) {
            log.error("Invalid campaign: budget must be positive");
            throw new InvalidCampaignException("Budget must be greater than zero");
        }

        if (campaign.getStartDate() == null || campaign.getEndDate() == null) {
            log.error("Invalid campaign: dates missing");
            throw new InvalidCampaignException("Start and End dates are required");
        }

        if (campaign.getEndDate().isBefore(campaign.getStartDate())) {
            log.error("Invalid campaign: endDate before startDate");
            throw new InvalidCampaignException("End date must be after start date");
        }
    }



    @Override
    @Transactional
    public void handleAuctionResult(AuctionResultEvent event) {

        Campaign campaign = campaignRepository.findById(event.getCampaignId())
                .orElseThrow(() -> new CampaignNotFoundException(event.getCampaignId()));

        double remaining = campaign.getRemainingBudget() - event.getWinningPrice();

        if (remaining < 0) {
            log.warn("Budget exceeded for campaignId={}", campaign.getId());
            return;
        }

        campaign.setRemainingBudget(remaining);

        campaignRepository.save(campaign);

        log.info("Budget updated campaignId={}, remaining={}",
                campaign.getId(), remaining);
    }
}