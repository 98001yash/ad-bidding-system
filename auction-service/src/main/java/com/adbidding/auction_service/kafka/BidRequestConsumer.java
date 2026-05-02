package com.adbidding.auction_service.kafka;


import com.adbidding.auction_service.enums.AuctionType;
import com.adbidding.auction_service.service.AuctionService;
import com.adbidding.events.BidRequestEvent;
import com.adbidding.events.common.KafkaTopics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidRequestConsumer {

    private final AuctionService auctionService;

    @KafkaListener(
            topics = KafkaTopics.BID_REQUEST,
            groupId = "auction-group",
            containerFactory = "bidRequestKafkaListenerContainerFactory"
    )
    public void consume(BidRequestEvent event) {

        log.info("Received BidRequestEvent requestId={}", event.getRequestId());

        try {

            // ⚠️ TEMP: simulate bids (until bidder-service is ready)
            List bids = List.of(); // placeholder

            auctionService.runAuction(
                    event.getRequestId(),
                    bids,
                    AuctionType.SECOND_PRICE
            );

        } catch (Exception e) {
            log.error("Error processing BidRequestEvent requestId={}", event.getRequestId(), e);
        }
    }
}