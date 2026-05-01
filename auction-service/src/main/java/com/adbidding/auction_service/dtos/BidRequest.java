package com.adbidding.auction_service.dtos;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequest {

    private String requestId;
    private Long userId;
    private String location;
    private String deviceType;
    private Long timestamp;

    @Builder.Default
    private String correlationId = UUID.randomUUID().toString();

    public static BidRequest create(Long userId, String location, String deviceType) {
        return BidRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .userId(userId)
                .location(location)
                .deviceType(deviceType)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
