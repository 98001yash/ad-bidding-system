package com.adbidding.bidder_service.exceptions;


import lombok.Getter;

@Getter
public class BidderServiceException extends RuntimeException {

    private final String errorCode;

    public BidderServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}