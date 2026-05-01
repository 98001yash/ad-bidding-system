package com.adbidding.campaign_service.service.Impl;


import com.adbidding.campaign_service.entity.AdCreative;
import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.exceptions.CampaignNotFoundException;
import com.adbidding.campaign_service.exceptions.InvalidCampaignException;
import com.adbidding.campaign_service.repository.AdCreativeRepository;
import com.adbidding.campaign_service.repository.CampaignRepository;
import com.adbidding.campaign_service.service.AdCreativeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdCreativeServiceImpl implements AdCreativeService {


    private final AdCreativeRepository adCreativeRepository;
    private final CampaignRepository campaignRepository;

    @Override
    @Transactional
    public AdCreative createAdCreative(Long campaignId, AdCreative adCreative) {

        log.info("Creating ad for campaignId={}",campaignId);

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(()-> {
                    log.error("Campaign not found with id={}",campaignId);
                    return new CampaignNotFoundException(campaignId);
                });

        validateAd(adCreative);

        adCreative.setCampaign(campaign);

        AdCreative saved =  adCreativeRepository.save(adCreative);
        log.info("Ad created successfully with id={} for campaignId={}",saved.getId(), campaignId);

        return saved;
    }

    @Override
    public List<AdCreative> getAdsByCampaign(Long campaignId) {

        log.debug("Fetching ads for campaignId={}",campaignId);

        return adCreativeRepository.findByCampaignId(campaignId);
    }

    @Override
    public AdCreative getAdById(Long adId) {
        return null;
    }

    @Override
    public AdCreative updateAd(Long adId, AdCreative adCreative) {
        return null;
    }

    @Override
    public void deleteAd(Long adId) {

    }

    // helper
    private void validateAd(AdCreative ad) {

        if (ad.getTitle() == null || ad.getTitle().isBlank()) {
            log.error("Invalid ad: title missing");
            throw new InvalidCampaignException("Ad title is required");
        }

        if (ad.getBaseBid() == null || ad.getBaseBid() <= 0) {
            log.error("Invalid ad: baseBid invalid");
            throw new InvalidCampaignException("Base bid must be greater than zero");
        }

        if (ad.getRedirectUrl() == null || ad.getRedirectUrl().isBlank()) {
            log.error("Invalid ad: redirect URL missing");
            throw new InvalidCampaignException("Redirect URL is required");
        }
    }
}
