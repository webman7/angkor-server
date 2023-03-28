package com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.target.AdGroupDemographicTargetDto;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.advertiser.campaign.dto.CampaignIdGetter;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
public abstract class AdGroupDto {
    public static abstract class Request {
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class FirstSave {
            @Valid
            private AdGroupDemographicTargetDto.Request.FirstSave demographicTarget;
            @Valid
            private AdGroupScheduleDto.Request.FirstSave adGroupSchedule;
            @NotNull
            @Size(min = 1)
            private List<String> media;
            @NotNull
            @Size(min = 1)
            private List<String> devices;
            @NotBlank
            @Size(min = 1, max = 50)
            private String name;
            private AdGroup.Pacing pacing;
            private AdGroup.PacingType pacingType;
            private Long bidAmount;
            private AdGroup.BidStrategy bidStrategy;
            private Long dailyBudgetAmount;
            private Long budgetAmount;
            private boolean fullDeviceDisplay;
            private boolean onlyWifiDisplay;
            private boolean allMedia;
            private boolean onlyAdult;
        }

        @Getter
        @Setter
        public static class Save implements CampaignIdGetter {
            @NotNull
            private Integer campaignId;
            @NotNull
            @Size(min = 1)
            private List<FirstSave> adGroups;
        }

        @Getter
        @Setter
        public static class Search implements AdAccountIdGetter {
            @NotNull
            private Integer adAccountId;
            private Integer campaignId;
            private String name;
        }

        @Getter
        @Setter
        public static class PlacementMedia {
            private Integer placementId;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Update extends FirstSave implements AdGroupIdGetter {
            @NotNull
            private Integer adGroupId;
        }

        @Getter
        @Setter
        public static class Copy implements AdGroupIdGetter {
            @NotNull
            private Integer campaignId;
            @NotNull
            private Integer adGroupId;
            private boolean onlyAdGroup;
            private boolean changeStartEndDate;
            private Integer startDate;
            private Integer endDate;
        }
    }

    public static abstract class Response {
        @Getter
        @AllArgsConstructor
        public static class FirstStartDateAndLastEndDate {
            private Integer campaignId;
            private Integer firstStartDate;
            private Integer lastEndDate;
        }

        @Getter
        @Setter
        public static class AdvertiserSearch {
            private Integer id;
            private String adTypeName;
            private String adGoalName;
            private String name;
            private AdGroup.Pacing pacing;
            private AdGroup.PacingType pacingType;
            private Long bidAmount;
            private AdGroup.BidStrategy bidStrategy;
            private Long dailyBudgetAmount;
            private Long budgetAmount;
            private AdGroup.Config config;
            private AdGroup.SystemConfig systemConfig;
            private AdGroup.Status status;
            private Integer campaignId;
            private String campaignName;
            private Integer scheduleStartDate;
            private Integer scheduleEndDate;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private ReportDto.Response report;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class ForSaveCreative {
            private Integer id;
            private String name;
            private Integer campaignId;
            private String campaignName;
        }

        @Getter
        @Setter
        public static class Detail {
            private Integer adGroupId;
            private CampaignDto.Response.Detail campaign;
            private AdGroupDemographicTargetDto.Response.Default demographicTarget;
            private AdGroupScheduleDto.Response.Default adGroupSchedule;
            private List<String> media;
            private List<String> devices;
            private String name;
            private AdGroup.Pacing pacing;
            private AdGroup.PacingType pacingType;
            private Long bidAmount;
            private AdGroup.BidStrategy bidStrategy;
            private Long dailyBudgetAmount;
            private Long budgetAmount;
            private boolean fullDeviceDisplay;
            private boolean onlyWifiDisplay;
            private boolean allMedia;
            private boolean onlyAdult;
            private AdGroup.Config config;
            private AdGroup.SystemConfig systemConfig;
            private AdGroup.Status status;
        }

        @Getter
        @Setter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class Budget {
            private Integer adGroupId;
            private Long budgetAmount;

            @QueryProjection
            public Budget(Integer adGroupId, Long budgetAmount) {
                this.adGroupId = adGroupId;
                this.budgetAmount = budgetAmount;
            }
        }

        @Getter
        @AllArgsConstructor
        public static class Placement {
            private Integer id;
            private String name;
            private Integer width;
            private Integer height;
            private String widthHeightRate;
            private String memo;
            private String adminMemo;

        }

        @Getter
        @AllArgsConstructor
        public static class Category {
            private Integer categoryId;
            private String categoryName;

        }

        @Getter
        @AllArgsConstructor
        public static class CategoryMedia {
            private Integer categoryId;
            private String categoryName;
            private Integer mediaId;
            private String mediaName;

        }

        @Getter
        @AllArgsConstructor
        public static class PlacementMedia {
            private Integer categoryId;
            private String categoryName;
            private Integer mediaId;
            private String mediaName;

        }

    }
}
