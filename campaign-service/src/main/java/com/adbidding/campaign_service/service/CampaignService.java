package com.adbidding.campaign_service.service;

import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.events.AuctionResultEvent;

import java.util.List;

public interface CampaignService {


    Campaign createCampaign(Campaign campaign);

    Campaign getCampaignById(Long id);

    List<Campaign> getActiveCampaigns();

    Campaign updateCampaign(Long id, Campaign campaign);

    void deleteCampaign(Long id);

    void handleAuctionResult(AuctionResultEvent event);
}
