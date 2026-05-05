package com.adbidding.bidder_service.kafka;


import com.adbidding.events.BidResponseEvent;
import com.adbidding.events.common.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidResponseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishBidResponse(BidResponseEvent event){

        log.info("Publishing BidResponseEvent requestId={}, bidderId={}, price={}",
                event.getRequestId(),
                event.getBidderId(),
                event.getBidPrice());

        kafkaTemplate.send(
                KafkaTopics.BID_RESPONSE,
                event.getRequestId(),
                event
        );
    }
}
