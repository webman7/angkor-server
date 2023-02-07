package com.adplatform.restApi.domain.advertiser.campaign.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdvertiserSearchAdGroupCondition;
import com.adplatform.restApi.domain.advertiser.creative.dto.AdvertiserSearchCreativeCondition;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class AdvertiserSearchRequest implements AdAccountIdGetter {
    @NotNull
    private Integer adAccountId;
    @NotNull
    private Integer reportStartDate;
    @NotNull
    private Integer reportEndDate;
    private List<String> adTypeNames;
    private List<String> adGoalNames;
    @Valid
    private AdvertiserSearchCampaignCondition campaignCondition;
    @Valid
    private AdvertiserSearchAdGroupCondition adGroupCondition;
    @Valid
    private AdvertiserSearchCreativeCondition creativeCondition;
    private List<String> indicators;
    private String reportLevel;
}
