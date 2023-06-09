package com.adplatform.restApi.domain.advertiser.campaign.api;

import com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal.AdTypeAndGoalRepository;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdTypeAndGoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ad-type-goal")
public class AdTypeAndGoalApi {
    private final AdTypeAndGoalRepository adTypeAndGoalRepository;

    @GetMapping("/{campaignId}")
    public AdTypeAndGoalDto findByCampaignId(@PathVariable Integer campaignId) {
        return this.adTypeAndGoalRepository.findByCampaignId(campaignId);
    }
}
