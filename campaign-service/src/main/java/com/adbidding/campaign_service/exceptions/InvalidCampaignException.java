package com.adbidding.campaign_service.exceptions;


public class InvalidCampaignException extends CampaignServiceException {

    public InvalidCampaignException(String message) {
        super(message, "INVALID_CAMPAIGN");
    }
}