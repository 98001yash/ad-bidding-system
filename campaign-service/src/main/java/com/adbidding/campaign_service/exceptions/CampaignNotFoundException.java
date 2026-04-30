package com.adbidding.campaign_service.exceptions;

public class CampaignNotFoundException extends CampaignServiceException {

    public CampaignNotFoundException(Long campaignId) {
        super("Campaign not found with id: " + campaignId, "CAMPAIGN_NOT_FOUND");
    }
}