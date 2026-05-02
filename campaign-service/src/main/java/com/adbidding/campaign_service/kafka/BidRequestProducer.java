package com.adbidding.campaign_service.kafka;

import com.adbidding.events.BidRequestEvent;
import com.adbidding.events.common.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidRequestProducer {

    private final KafkaEventProducer kafkaEventProducer;

    public void publishBidRequest(BidRequestEvent event){

        log.info("Publishing BidRequestEvent with requestId={}",event.getRequestId());

        kafkaEventProducer.sendEvent(
                KafkaTopics.BID_REQUEST,
                event.getRequestId(),
                event
        );
    }
}
