package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.domain.creative.domain.Creative;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AdvertiserSearchRequest implements AdAccountIdGetter {
    @NotNull
    private Integer adAccountId;
    private List<Integer> campaignIds;
    private String campaignName;
    private List<Campaign.Config> campaignConfigs;
    private List<Campaign.Status> campaignStatuses;
    private List<Integer> adGroupIds;
    private String adGroupName;
    private List<AdGroup.Config> adGroupConfigs;
    private List<AdGroup.Status> adGroupStatuses;
    private List<Integer> creativeIds;
    private String creativeName;
    private List<Creative.Format> creativeFormats;
    private List<Creative.Config> creativeConfigs;
    private List<Creative.Status> creativeStatuses;
    private List<Creative.ReviewStatus> creativeReviewStatuses;
    private List<String> adTypeNames;
    private List<String> adGoalNames;
}
