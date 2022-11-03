package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import java.util.List;
import java.util.Map;

public interface AdGroupQuerydslRepository {
    Map<Integer, Integer> findScheduleFirstStartDateByCampaignId(List<Integer> campaignIds);

    Map<Integer, Integer> findScheduleLastEndDateByCampaignId(List<Integer> campaignIds);
}
