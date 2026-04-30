package com.adbidding.campaign_service.exceptions;


public class BudgetExceededException extends CampaignServiceException {

    public BudgetExceededException(Long campaignId) {
        super("Budget exceeded for campaign: " + campaignId, "BUDGET_EXCEEDED");
    }
}