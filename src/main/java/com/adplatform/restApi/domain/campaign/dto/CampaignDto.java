package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class CampaignDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save implements AdAccountIdGetter {
            @NotNull
            private Integer adAccountId;
            @NotNull
            @Valid
            private AdTypeAndGoalDto adTypeAndGoal;
            @Size(min = 1)
            @Valid
            private List<AdGroupDto.Request.FirstSave> adGroups;
            @Size(min = 2)
            @NotNull
            private String name;
            @NotNull
            private Long dailyBudgetAmount;
            private Campaign.GoalType goalType;
            private String trackingId;
            private Campaign.TrackingType trackingType;

            @Override
            public Integer getAdAccountId() {
                return this.adAccountId;
            }
        }

        @Getter
        @Setter
        public static class Search implements AdAccountIdGetter {
            @NotNull
            private Integer adAccountId;
            private String name;
            private String adTypeName;
            private String adGoalName;
        }

        @Getter
        @Setter
        public static class Update extends Response.ForUpdate implements CampaignIdGetter {
        }
    }
    public static abstract class Response {
        @Getter
        @Setter
        @Accessors(chain = true)
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
            private Integer adGroupSchedulesFirstStartDate;
            private Integer adGroupSchedulesLastEndDate;

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
                    LocalDateTime updatedAt,
                    Integer adGroupSchedulesFirstStartDate,
                    Integer adGroupSchedulesLastEndDate) {
                this.id = id;
                this.adTypeAndGoal = adTypeAndGoal;
                this.name = name;
                this.dailyBudgetAmount = dailyBudgetAmount;
                this.config = config;
                this.systemConfig = systemConfig;
                this.status = status;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
                this.adGroupSchedulesFirstStartDate = adGroupSchedulesFirstStartDate;
                this.adGroupSchedulesLastEndDate = adGroupSchedulesLastEndDate;
            }
        }

        @Getter
        @Setter
        public static class ForSaveAdGroup {
            private Integer id;
            private String name;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private AdTypeAndGoalDto adTypeAndGoal;

            @QueryProjection
            public ForSaveAdGroup(Integer id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, AdTypeAndGoalDto adTypeAndGoal) {
                this.id = id;
                this.name = name;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
                this.adTypeAndGoal = adTypeAndGoal;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ForUpdate {
            private Integer campaignId;
            @Size(min = 2)
            @NotNull
            private String name;
            @NotNull
            private Long dailyBudgetAmount;
            private Campaign.GoalType goalType;
            private String trackingId;
            private Campaign.TrackingType trackingType;

            @QueryProjection
            public ForUpdate(Integer campaignId, String name, Long dailyBudgetAmount, Campaign.GoalType goalType, String trackingId, Campaign.TrackingType trackingType) {
                this.campaignId = campaignId;
                this.name = name;
                this.dailyBudgetAmount = dailyBudgetAmount;
                this.goalType = goalType;
                this.trackingId = trackingId;
                this.trackingType = trackingType;
            }
        }
    }
}
