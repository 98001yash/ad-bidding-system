package com.adbidding.campaign_service.kafka;


import com.adbidding.campaign_service.service.CampaignService;
import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.common.KafkaTopics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionResultConsumer {

    private final CampaignService campaignService;

    @KafkaListener(
            topics = KafkaTopics.AUCTION_RESULT,
            groupId = "campaign-group",
            containerFactory = "auctionResultKafkaListenerContainerFactory"
    )
    public void consume(AuctionResultEvent event) {

        log.info("Received AuctionResultEvent requestId={}, campaignId={}, price={}",
                event.getRequestId(),
                event.getCampaignId(),
                event.getWinningPrice());

        try {
            campaignService.handleAuctionResult(event);
        } catch (Exception e) {
            log.error("Error handling auction result requestId={}", event.getRequestId(), e);
        }
    }
}