package com.adbidding.campaign_service.repository;


import com.adbidding.campaign_service.entity.TargetingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TargetingCriteriaRepository extends JpaRepository<TargetingCriteria, Long> {

    // Get targeting by campaign
    Optional<TargetingCriteria> findByCampaignId(Long campaignId);
}