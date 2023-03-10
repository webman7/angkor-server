package com.adplatform.restApi.domain.advertiser.campaign.dto;

import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.statistics.dto.ReportCondition;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Setter
public class AdvertiserSearchCampaignCondition {
    private List<Integer> ids;
    private String name;
    private List<Campaign.Config> configs;
    private List<Campaign.Status> statuses;
    @Valid
    private List<ReportCondition> reportConditions;
}
