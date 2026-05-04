package com.adbidding.bidder_service.advices;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
}