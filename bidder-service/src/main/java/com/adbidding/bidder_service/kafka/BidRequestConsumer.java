package com.adbidding.bidder_service.kafka;


import com.adbidding.bidder_service.exceptions.InvalidBidRequestException;
import com.adbidding.events.BidRequestEvent;
import com.adbidding.events.BidResponseEvent;
import com.adbidding.events.common.KafkaTopics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidRequestConsumer {

    private final BidResponseProducer bidResponseProducer;
    private final Random random = new Random();

    @KafkaListener(
            topics = KafkaTopics.BID_REQUEST,
            groupId = "bidder-group",
            containerFactory = "bidRequestKafkaListenerContainerFactory"
    )
    public void consume(BidRequestEvent event) {

        log.info("Received BidRequestEvent requestId={}", event.getRequestId());

        try {
            validate(event);

            double bidPrice = generateBidPrice();

            BidResponseEvent response = BidResponseEvent.builder()
                    .requestId(event.getRequestId())
                    .adId(generateAdId())
                    .campaignId(event.getCampaignId())
                    .bidPrice(bidPrice)
                    .bidderId("bidder-service-1")
                    .timestamp(System.currentTimeMillis())
                    .build();

            bidResponseProducer.publishBidResponse(response);

        } catch (Exception e) {
            log.error("Error processing BidRequestEvent requestId={}", event.getRequestId(), e);
        }
    }


    private void validate(BidRequestEvent event) {
        if (event.getRequestId() == null) {
            throw new InvalidBidRequestException("RequestId is null");
        }
    }

    private double generateBidPrice() {
        return 1 + (10 * random.nextDouble());
    }

    private Long generateAdId() {
        return Math.abs(random.nextLong() % 1000);
    }

}