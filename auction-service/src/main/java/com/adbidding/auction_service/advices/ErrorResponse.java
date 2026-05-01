package com.adbidding.auction_service.advices;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(String message, String errorCode, String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}