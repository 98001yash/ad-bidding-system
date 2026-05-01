package com.adbidding.events;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidResponse4Event {

    private String requestId;
    private Long adId;

    private Long campaignId;
    private Double bidPrice;
    private String bidderId;

    private Long latencyMs;
    private boolean valid;
}
