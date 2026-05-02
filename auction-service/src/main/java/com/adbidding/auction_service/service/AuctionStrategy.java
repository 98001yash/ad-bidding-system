package com.adbidding.auction_service.service;

import com.adbidding.events.BidResponseEvent;

import java.util.List;

public interface AuctionStrategy {

    BidResponseEvent selectWinner(List<BidResponseEvent> bids);

    double calculatePrice(List<BidResponseEvent> bids);
}
