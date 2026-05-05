package com.adbidding.auction_service.kafka;


import com.adbidding.auction_service.enums.AuctionType;
import com.adbidding.auction_service.service.AuctionService;
import com.adbidding.events.BidResponseEvent;
import com.adbidding.events.common.KafkaTopics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidResponseConsumer {

    private final AuctionService auctionService;
    private final AuctionResultProducer auctionResultProducer;

    // Store bids temporarily
    private final Map<String, List<BidResponseEvent>> bidStore = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(2);

    @KafkaListener(
            topics = KafkaTopics.BID_RESPONSE,
            groupId = "auction-group"
    )
    public void consume(BidResponseEvent event) {

        log.info("Received BidResponseEvent requestId={}, bidder={}, price={}",
                event.getRequestId(),
                event.getBidderId(),
                event.getBidPrice());

        // Store bids
        bidStore.computeIfAbsent(event.getRequestId(), k -> new CopyOnWriteArrayList<>())
                .add(event);

        // Delay auction (collect multiple bids)
        scheduler.schedule(() -> runAuction(event.getRequestId()),
                100, TimeUnit.MILLISECONDS);
    }

    private void runAuction(String requestId) {

        List<BidResponseEvent> bids = bidStore.get(requestId);

        if (bids == null || bids.isEmpty()) {
            log.warn("No bids for requestId={}", requestId);
            return;
        }

        try {
            var result = auctionService.runAuction(
                    requestId,
                    bids,
                    AuctionType.SECOND_PRICE
            );

            log.info("Auction result requestId={}, winnerAd={}, price={}",
                    requestId,
                    result.getWinningAdId(),
                    result.getWinningPrice());

            auctionResultProducer.publishResult(result);

        } catch (Exception e) {
            log.error("Auction failed requestId={}", requestId, e);
        } finally {
            bidStore.remove(requestId);
        }
    }
}