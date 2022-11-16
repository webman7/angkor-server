package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.dto.AdTypeAndGoalDto;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdTypeAndGoalQuerydslRepository {
    AdTypeAndGoalDto findByCampaignId(Integer campaignId);
}
