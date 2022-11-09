package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.dto.AdTypeAndGoalDto;

public interface AdTypeAndGoalQuerydslRepository {
    AdTypeAndGoalDto findByCampaignId(Integer campaignId);
}
