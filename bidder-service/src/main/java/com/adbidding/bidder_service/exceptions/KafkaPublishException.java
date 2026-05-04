package com.adbidding.bidder_service.exceptions;


public class KafkaPublishException extends BidderServiceException {

    public KafkaPublishException(String message) {
        super(message, "KAFKA_PUBLISH_FAILED");
    }
}