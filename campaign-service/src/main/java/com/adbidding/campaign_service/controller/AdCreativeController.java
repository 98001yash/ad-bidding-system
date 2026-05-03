package com.adbidding.campaign_service.controller;


import com.adbidding.campaign_service.entity.AdCreative;
import com.adbidding.campaign_service.service.AdCreativeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
@Slf4j
public class AdCreativeController {

    private final AdCreativeService adCreativeService;

    // CREATE AD
    @PostMapping("/campaign/{campaignId}")
    public ResponseEntity<AdCreative> createAd(
            @PathVariable Long campaignId,
            @RequestBody AdCreative adCreative
    ) {
        log.info("API: Create ad for campaignId={}", campaignId);
        return ResponseEntity.ok(
                adCreativeService.createAdCreative(campaignId, adCreative)
        );
    }

    // GET ADS BY CAMPAIGN
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<AdCreative>> getAdsByCampaign(
            @PathVariable Long campaignId
    ) {
        return ResponseEntity.ok(
                adCreativeService.getAdsByCampaign(campaignId)
        );
    }

    // GET AD BY ID
    @GetMapping("/{adId}")
    public ResponseEntity<AdCreative> getAd(@PathVariable Long adId) {
        return ResponseEntity.ok(
                adCreativeService.getAdById(adId)
        );
    }

}