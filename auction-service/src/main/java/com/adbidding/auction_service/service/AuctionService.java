package com.adbidding.auction_service.service;

import com.adbidding.auction_service.enums.AuctionType;
import com.adbidding.events.AuctionResultEvent;
import com.adbidding.events.BidResponseEvent;

import java.util.List;

public interface AuctionService {


    AuctionResultEvent runAuction(
            String requestId,
            List<BidResponseEvent> bids,
            AuctionType auctionType
    );
}