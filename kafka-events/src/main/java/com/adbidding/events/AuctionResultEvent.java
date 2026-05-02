package com.adbidding.events;


import com.adbidding.events.enums.AuctionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResultEvent {

    private String requestId;
    private Long winningAdId;
    private Long campaignId;
    private Double winningPrice;
    private String bidderId;
    private AuctionStatus status;

    private Long processedAt;
}