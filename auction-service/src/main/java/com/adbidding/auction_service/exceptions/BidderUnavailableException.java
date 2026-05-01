package com.adbidding.auction_service.exceptions;

public class BidderUnavailableException extends AuctionServiceException {

    public BidderUnavailableException(String bidderName) {
        super("Bidder unavailable: " + bidderName, "BIDDER_UNAVAILABLE");
    }
}