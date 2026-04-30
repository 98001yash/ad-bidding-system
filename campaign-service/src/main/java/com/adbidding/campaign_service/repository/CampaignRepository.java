package com.adbidding.campaign_service.repository;

import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign,Long> {


    // get all active campaign
    List<Campaign> findByStatus(CampaignStatus status);


    // get campaigns active within time window
    List<Campaign> findByStartDateBeforeAndEndDateAfter(
            LocalDateTime now1,
            LocalDateTime now2
    );



    // combined filter (used in bidding systems)
    List<Campaign> findByStatusAndStartDateBeforeAndEndDateAfter(
            CampaignStatus status,
            LocalDateTime now1,
            LocalDateTime now2
    );
}
