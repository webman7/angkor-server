package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    public static abstract class Response {
        @Getter
        @Setter
        public static class Page {
            private Integer id;
            private AdTypeAndGoalDto adTypeAndGoal;
            private String name;
            private Long dailyBudgetAmount;
            private Campaign.Config config;
            private Campaign.SystemConfig systemConfig;
            private Campaign.Status status;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;

            @QueryProjection
            public Page(
                    Integer id,
                    AdTypeAndGoalDto adTypeAndGoal,
                    String name,
                    Long dailyBudgetAmount,
                    Campaign.Config config,
                    Campaign.SystemConfig systemConfig,
                    Campaign.Status status,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
                this.id = id;
                this.adTypeAndGoal = adTypeAndGoal;
                this.name = name;
                this.dailyBudgetAmount = dailyBudgetAmount;
                this.config = config;
                this.systemConfig = systemConfig;
                this.status = status;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
            }
        }
    }
}
