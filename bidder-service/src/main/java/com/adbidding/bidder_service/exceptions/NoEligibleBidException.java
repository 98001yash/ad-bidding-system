package com.adbidding.bidder_service.exceptions;


public class NoEligibleBidException extends BidderServiceException {

    public NoEligibleBidException(String requestId) {
        super("No eligible bidders for requestId=" + requestId, "NO_ELIGIBLE_BID");
    }
}