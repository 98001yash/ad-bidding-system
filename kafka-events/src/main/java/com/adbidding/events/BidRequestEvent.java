package com.adbidding.events;




import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequestEvent {

    private String requestId;
    private Long userId;
    private String location;
    private String deviceType;
    private Long timestamp;

    @Builder.Default
    private String correlationId = UUID.randomUUID().toString();

    public static BidRequestEvent create(Long userId, String location, String deviceType) {
        return BidRequestEvent.builder()
                .requestId(UUID.randomUUID().toString())
                .userId(userId)
                .location(location)
                .deviceType(deviceType)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
