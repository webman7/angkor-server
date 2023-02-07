package com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal;

import com.adplatform.restApi.domain.advertiser.campaign.dto.AdTypeAndGoalDto;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdTypeAndGoalQuerydslRepository {
    AdTypeAndGoalDto findByCampaignId(Integer campaignId);
}
