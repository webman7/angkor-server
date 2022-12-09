package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdvertiserSearchAdGroupCondition;
import com.adplatform.restApi.domain.creative.dto.AdvertiserSearchCreativeCondition;
import lombok.Getter;
import lombok.Setter;

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
    private AdvertiserSearchCampaignCondition campaignCondition;
    private AdvertiserSearchAdGroupCondition adGroupCondition;
    private AdvertiserSearchCreativeCondition creativeCondition;
}