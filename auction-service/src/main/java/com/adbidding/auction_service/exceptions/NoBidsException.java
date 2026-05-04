package com.adbidding.auction_service.exceptions;

public class NoBidsException extends AuctionServiceException {

    public NoBidsException(String requestId) {
        super("No bids received for requestId=" + requestId, "NO_BIDS");
    }
}