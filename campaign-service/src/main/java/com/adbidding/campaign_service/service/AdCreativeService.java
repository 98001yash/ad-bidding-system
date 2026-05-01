package com.adbidding.campaign_service.service;

import com.adbidding.campaign_service.entity.AdCreative;

import java.util.List;

public interface AdCreativeService {

    AdCreative createAdCreative(Long campaignId, AdCreative adCreative);

    List<AdCreative> getAdsByCampaign(Long campaignId);

    AdCreative getAdById(Long adId);

    AdCreative updateAd(Long adId, AdCreative adCreative);

    void deleteAd(Long adId);
}
