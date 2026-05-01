package com.adbidding.auction_service.dtos;


import com.adbidding.auction_service.enums.AuctionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResult {

    private String requestId;
    private Long winningAdId;
    private Long campaignId;
    private Double winningPrice;
    private String bidderId;
    private AuctionStatus status;

    private Long processedAt;
}