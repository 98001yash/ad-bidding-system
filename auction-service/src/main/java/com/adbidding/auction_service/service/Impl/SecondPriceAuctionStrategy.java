package com.adbidding.auction_service.service.Impl;



import com.adbidding.auction_service.service.AuctionStrategy;
import com.adbidding.events.BidResponseEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SecondPriceAuctionStrategy implements AuctionStrategy {

    @Override
    public BidResponseEvent selectWinner(List<BidResponseEvent> bids) {
        return bids.stream()
                .max(Comparator.comparingDouble(BidResponseEvent::getBidPrice))
                .orElse(null);
    }

    @Override
    public double calculatePrice(List<BidResponseEvent> bids) {

        List<Double> sorted = bids.stream()
                .map(BidResponseEvent::getBidPrice)
                .sorted(Comparator.reverseOrder())
                .toList();

        if (sorted.size() < 2) {
            return sorted.get(0); // fallback
        }

        return sorted.get(1); // second highest
    }
}