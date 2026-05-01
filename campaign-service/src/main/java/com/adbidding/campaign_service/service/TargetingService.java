package com.adbidding.campaign_service.service;

import com.adbidding.campaign_service.entity.Campaign;

import java.util.List;

public interface TargetingService {

    List<Campaign> getEligibleCampaigns(Long userId, String location,
                                        String deviceType);
}
