package com.adbidding.campaign_service.controller;

import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.service.CampaignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
@Slf4j
public class CampaignController {

    private final CampaignService campaignService;

    // CREATE CAMPAIGN
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        log.info("API: Create campaign");
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getCampaignById(id));
    }


    @GetMapping("/active")
    public ResponseEntity<List<Campaign>> getActiveCampaigns(){
        return ResponseEntity.ok(campaignService.getActiveCampaigns());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaigns(
            @PathVariable Long id,
            @RequestBody Campaign campaign
    ){
        return ResponseEntity.ok(campaignService.updateCampaign(id, campaign));

    }
}
