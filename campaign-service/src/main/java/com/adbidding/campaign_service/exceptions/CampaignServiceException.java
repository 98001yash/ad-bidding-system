package com.adbidding.campaign_service.exceptions;


import lombok.Getter;

@Getter
public class CampaignServiceException extends RuntimeException {

    private final String errorCode;

    public CampaignServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}