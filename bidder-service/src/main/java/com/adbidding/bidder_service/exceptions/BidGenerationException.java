package com.adbidding.bidder_service.exceptions;


public class BidGenerationException extends BidderServiceException {

    public BidGenerationException(String message) {
        super(message, "BID_GENERATION_FAILED");
    }
}