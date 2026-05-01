package com.adbidding.campaign_service.service.Impl;







import com.adbidding.campaign_service.entity.Campaign;
import com.adbidding.campaign_service.entity.TargetingCriteria;
import com.adbidding.campaign_service.enums.CampaignStatus;
import com.adbidding.campaign_service.repository.CampaignRepository;
import com.adbidding.campaign_service.repository.TargetingCriteriaRepository;
import com.adbidding.campaign_service.service.TargetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TargetServiceImpl implements TargetingService {

    private final CampaignRepository campaignRepository;
    private final TargetingCriteriaRepository targetingRepository;


    @Override
    public List<Campaign> getEligibleCampaigns(Long userId, String location, String deviceType) {

        log.info("Running targeting for userId={}, location={}, device={}", userId, location, deviceType);

        LocalDateTime now = LocalDateTime.now();

        // Step 1: Fetch active campaigns
        List<Campaign> campaigns = campaignRepository
                .findByStatusAndStartDateBeforeAndEndDateAfter(
                        CampaignStatus.ACTIVE,
                        now,
                        now
                );

        log.debug("Fetched {} active campaigns", campaigns.size());

        List<Campaign> eligibleCampaigns = new ArrayList<>();

        // Step 2: Apply filtering
        for (Campaign campaign : campaigns) {

            Optional<TargetingCriteria> optionalCriteria =
                    targetingRepository.findByCampaignId(campaign.getId());

            if (optionalCriteria.isEmpty()) {
                log.debug("No targeting criteria for campaignId={}", campaign.getId());
                continue;
            }

            TargetingCriteria criteria = optionalCriteria.get();

            if (matchesCriteria(criteria, location, deviceType)) {
                eligibleCampaigns.add(campaign);
            }
        }

        log.info("Eligible campaigns count={}", eligibleCampaigns.size());

        return eligibleCampaigns;
    }


    // MATCHING LOGIC
    private boolean matchesCriteria(TargetingCriteria criteria,
                                    String location,
                                    String deviceType) {

        // Location filter
        if (criteria.getLocation() != null &&
                !criteria.getLocation().equalsIgnoreCase(location)) {
            return false;
        }

        // Device filter
        if (criteria.getDeviceType() != null &&
                !criteria.getDeviceType().equalsIgnoreCase(deviceType)) {
            return false;
        }

        return true;
    }
}