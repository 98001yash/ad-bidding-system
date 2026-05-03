package com.adbidding.auction_service.controller;



import com.adbidding.auction_service.enums.AuctionType;
import com.adbidding.auction_service.service.AuctionService;
import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.BidResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auction")
@RequiredArgsConstructor
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;


    @PostMapping("/run")
    public AuctionResultEvent runAuction(
            @RequestParam(defaultValue = "manual-test") String requestId,
            @RequestParam(defaultValue = "SECOND_PRICE") AuctionType auctionType,
            @RequestBody List<BidResponseEvent> bids
    ) {

        log.info("API: Running auction manually, requestId={}, type={}, bids={}",
                requestId, auctionType, bids.size());

        return auctionService.runAuction(
                requestId,
                bids,
                auctionType
        );
    }
}