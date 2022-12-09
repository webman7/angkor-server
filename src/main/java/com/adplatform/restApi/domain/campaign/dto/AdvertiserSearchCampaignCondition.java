package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.statistics.dto.ReportCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class AdvertiserSearchCampaignCondition {
    private List<Integer> ids;
    private String name;
    private List<Campaign.Config> configs;
    private List<Campaign.Status> statuses;
    private List<ReportCondition> reportConditions;
}
