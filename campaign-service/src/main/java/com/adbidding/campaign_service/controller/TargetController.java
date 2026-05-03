package com.adbidding.campaign_service.controller;

import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.service.TargetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/targeting")
@RequiredArgsConstructor
@Slf4j
public class TargetController {

    private final TargetingService targetingService;

    // GET ELIGIBLE CAMPAIGNS
    @GetMapping("/eligible")
    public ResponseEntity<List<Campaign>> getEligibleCampaigns(
            @RequestParam Long userId,
            @RequestParam String location,
            @RequestParam String deviceType
    ) {

        log.info("API: Targeting request userId={}, location={}, device={}",
                userId, location, deviceType);

        return ResponseEntity.ok(
                targetingService.getEligibleCampaigns(userId, location, deviceType)
        );
    }
}