package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public abstract class CampaignDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @NotNull
            @Valid
            private AdTypeAndGoalDto adTypeAndGoal;
            @Size(min = 1)
            @Valid
            private List<AdGroupDto.Request.FirstSave> adGroups;
            @Size(min = 2)
            private String name;
            @NotNull
            private Long dailyBudgetAmount;
            private Campaign.GoalType goalType;
            private String trackingId;
            private Campaign.TrackingType trackingType;
        }
    }
}
