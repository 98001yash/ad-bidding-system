package com.adbidding.auction_service.exceptions;

public class AuctionTimeoutException extends AuctionServiceException {

    public AuctionTimeoutException(Long requestId) {
        super("Auction timed out for requestId=" + requestId, "AUCTION_TIMEOUT");
    }
}