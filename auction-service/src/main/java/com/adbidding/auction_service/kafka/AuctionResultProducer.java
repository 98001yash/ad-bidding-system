package com.adbidding.auction_service.kafka;

import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.common.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionResultProducer {

    private final KafkaTemplate<String, AuctionResultEvent> kafkaTemplate;

    public void publishResult(AuctionResultEvent event) {

        log.info("Publishing AuctionResultEvent requestId={}, campaignId={}, winnerAd={}, price={}",
                event.getRequestId(),
                event.getCampaignId(),
                event.getWinningAdId(),
                event.getWinningPrice());

        kafkaTemplate.send(
                KafkaTopics.AUCTION_RESULT,
                event.getRequestId(),
                event
        );
    }
}