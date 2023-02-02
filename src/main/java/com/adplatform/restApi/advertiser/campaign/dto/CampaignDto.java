package com.adplatform.restApi.advertiser.campaign.dto;

import com.adplatform.restApi.advertiser.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.advertiser.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
            @NotNull
            private Long budgetAmount;
            private Integer startDate;
            private Integer endDate;
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

        @Getter
        @Setter
        public static class DateUpdate implements CampaignIdGetter {
            @NotNull
            private Integer campaignId;
            private Integer startDate;
            private Integer endDate;
        }

//        @Getter
//        @Setter
//        public static class UpdateDate extends Response.ForDateUpdate implements CampaignIdGetter {
//        }
    }
    public static abstract class Response {
        @Getter
        @Setter
        public static class Page {
            private Integer id;
            private AdTypeAndGoalDto adTypeAndGoal;
            private String name;
            private Long dailyBudgetAmount;
            private Long budgetAmount;
            private Integer startDate;
            private Integer endDate;
            private String config;
            private String systemConfig;
            private String status;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private ReportDto.Response report;
        }

        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private AdTypeAndGoalDto adTypeAndGoal;
            private String name;
            private Long dailyBudgetAmount;
            private Long budgetAmount;
            private Integer startDate;
            private Integer endDate;
            private String config;
            private String systemConfig;
            private String status;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }

        @Getter
        @Setter
        public static class BaseDetail {
            private Integer id;
            private AdTypeAndGoalDto adTypeAndGoal;
            private String name;
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
            @NotNull
            private Long budgetAmount;
            private Campaign.GoalType goalType;
            private String trackingId;
            private Campaign.TrackingType trackingType;

            @QueryProjection
            public ForUpdate(Integer campaignId, String name, Long dailyBudgetAmount, Long budgetAmount, Campaign.GoalType goalType, String trackingId, Campaign.TrackingType trackingType) {
                this.campaignId = campaignId;
                this.name = name;
                this.dailyBudgetAmount = dailyBudgetAmount;
                this.budgetAmount = budgetAmount;
                this.goalType = goalType;
                this.trackingId = trackingId;
                this.trackingType = trackingType;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ForDateSave {
            private Integer campaignId;
            private Integer startDate;
            private Integer endDate;

            @QueryProjection
            public ForDateSave(Integer campaignId, Integer startDate, Integer endDate) {
                this.campaignId = campaignId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class ForDateUpdate {
            private Integer campaignId;
            private Integer startDate;
            private Integer endDate;

            @QueryProjection
            public ForDateUpdate(Integer campaignId, Integer startDate, Integer endDate) {
                this.campaignId = campaignId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Budget {
            private Integer campaignId;
            private Long budgetAmount;

            @QueryProjection
            public Budget(Integer campaignId, Long budgetAmount) {
                this.campaignId = campaignId;
                this.budgetAmount = budgetAmount;
            }
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class CampaignByAdAccountId {
            private Integer campaignId;
            private Integer adAccountId;

            private Long budgetAmount;

            @QueryProjection
            public CampaignByAdAccountId(Integer campaignId, Integer adAccountId, Long budgetAmount) {
                this.campaignId = campaignId;
                this.adAccountId = adAccountId;
                this.budgetAmount = budgetAmount;
            }
        }
    }
}
