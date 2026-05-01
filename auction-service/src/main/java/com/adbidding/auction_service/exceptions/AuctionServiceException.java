package com.adbidding.auction_service.exceptions;

import lombok.Getter;


@Getter
public class AuctionServiceException extends RuntimeException {

    private final String errorCode;

    public AuctionServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}