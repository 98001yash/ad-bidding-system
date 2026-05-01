package com.adbidding.auction_service.exceptions;


public class InvalidBidException extends AuctionServiceException {

    public InvalidBidException(String message) {
        super(message, "INVALID_BID");
    }
}