package com.adbidding.bidder_service.exceptions;


public class InvalidBidRequestException extends BidderServiceException {

    public InvalidBidRequestException(String message) {
        super(message, "INVALID_BID_REQUEST");
    }
}