package com.adplatform.restApi.advertiser.campaign.dto;

import com.adplatform.restApi.advertiser.adgroup.dto.adgroup.AdGroupDto;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetDto<T> {

    private List<CampaignDto.Response.Budget> campaigns;

    private List<AdGroupDto.Response.Budget> adGroups;

    public static <T> BudgetDto<T> create(List<CampaignDto.Response.Budget> campaigns, List<AdGroupDto.Response.Budget> adGroups) {
        return BudgetDto.<T>builder()
                .campaigns(campaigns)
                .adGroups(adGroups)
                .build();
    }
}