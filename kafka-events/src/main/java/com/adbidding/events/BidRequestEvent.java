package com.adbidding.events;




import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequestEvent {

    private Long campaignId;

    private String requestId;

    private Long userId;

    private String location;

    private String deviceType;

    private Long timestamp;

    @Builder.Default
    private String correlationId = UUID.randomUUID().toString();

    public static BidRequestEvent create(
            Long campaignId,
            Long userId,
            String location,
            String deviceType
    ) {

        return BidRequestEvent.builder()
                .campaignId(campaignId)
                .requestId(UUID.randomUUID().toString())
                .userId(userId)
                .location(location)
                .deviceType(deviceType)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
