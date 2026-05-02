package com.adbidding.auction_service.service.Impl;



import com.adbidding.auction_service.service.AuctionStrategy;
import com.adbidding.events.BidResponseEvent;

import java.util.Comparator;
import java.util.List;

public class FirstPriceAuctionStrategy implements AuctionStrategy {

    @Override
    public BidResponseEvent selectWinner(List<BidResponseEvent> bids) {
        return bids.stream()
                .max(Comparator.comparingDouble(BidResponseEvent::getBidPrice))
                .orElse(null);
    }

    @Override
    public double calculatePrice(List<BidResponseEvent> bids) {
        return bids.stream()
                .mapToDouble(BidResponseEvent::getBidPrice)
                .max()
                .orElse(0.0);
    }
}
