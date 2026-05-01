package com.adbidding.campaign_service.service.Impl;


import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.enums.CampaignStatus;
import com.adbidding.campaign_service.exceptions.InvalidCampaignException;
import com.adbidding.campaign_service.repository.CampaignRepository;
import com.adbidding.campaign_service.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;


    @Override
    public Campaign createCampaign(Campaign campaign) {

        log.info("Creating campaign with name={}",campaign.getName());
        validateCampaign(campaign)

        campaign.setStatus(CampaignStatus.ACTIVE);

        Campaign saved = campaignRepository.save(campaign);

        log.info("Campaign created successfully with id={}",saved.getId());
        return saved;
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return null;
    }

    @Override
    public List<Campaign> getActiveCampaigns() {
        return List.of();
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaign) {
        return null;
    }

    @Override
    public void deleteCampaign(Long id) {

    }


    // helper method
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
}
