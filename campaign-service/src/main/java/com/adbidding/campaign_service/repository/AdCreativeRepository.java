package com.adbidding.campaign_service.repository;

import com.adbidding.campaign_service.entity.AdCreative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdCreativeRepository extends JpaRepository<AdCreative, Long> {


    // get all ads for campaign
    List<AdCreative> findByCampaignId(Long campaignId);
}
