package com.adbidding.auction_service.service.Impl;


import com.adbidding.events.enums.AuctionStatus;
import com.adbidding.auction_service.enums.AuctionType;
import com.adbidding.auction_service.exceptions.NoBidsException;
import com.adbidding.auction_service.service.AuctionService;
import com.adbidding.auction_service.service.AuctionStrategy;
import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.BidResponseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    @Override
    public AuctionResultEvent runAuction(
            String requestId,
            List<BidResponseEvent> bids,
            AuctionType auctionType
    ) {

        log.info("Running auction for requestId={}, bidsCount={}, type={}",
                requestId, bids.size(), auctionType);

        // Step 1: Validate bids
        List<BidResponseEvent> validBids = bids.stream()
                .filter(this::isValidBid)
                .collect(Collectors.toList());

        if (validBids.isEmpty()) {
            log.warn("No valid bids for requestId={}", requestId);
            throw new NoBidsException(requestId);
        }

        // Step 2: Select strategy
        AuctionStrategy strategy = getStrategy(auctionType);

        // Step 3: Select winner
        BidResponseEvent winner = strategy.selectWinner(validBids);

        if (winner == null) {
            throw new NoBidsException(requestId);
        }

        // Step 4: Calculate price
        double price = strategy.calculatePrice(validBids);

        log.info("Auction winner: adId={}, bidder={}, price={}",
                winner.getAdId(), winner.getBidderId(), price);

        // Step 5: Build result
        return AuctionResultEvent.builder()
                .requestId(requestId)
                .winningAdId(winner.getAdId())
                .campaignId(winner.getCampaignId())
                .winningPrice(price)
                .bidderId(winner.getBidderId())
                .status(AuctionStatus.SUCCESS)
                .processedAt(System.currentTimeMillis())
                .build();
    }

    private boolean isValidBid(BidResponseEvent bid) {

        if (bid.getBidPrice() == null || bid.getBidPrice() <= 0) {
            log.error("Invalid bid detected: {}", bid);
            return false;
        }

        return true;
    }

    private AuctionStrategy getStrategy(AuctionType type) {

        return switch (type) {
            case FIRST_PRICE -> new FirstPriceAuctionStrategy();
            case SECOND_PRICE -> new SecondPriceAuctionStrategy();
        };
    }
}