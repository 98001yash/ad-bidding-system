package com.adbidding.auction_service.exceptions;


public class AuctionProcessingException extends AuctionServiceException {

    public AuctionProcessingException(String message) {
        super(message, "AUCTION_PROCESSING_ERROR");
    }
}