package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountIdGetter;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetDto;
import com.adplatform.restApi.domain.campaign.dto.CampaignIdGetter;
import com.adplatform.restApi.domain.statistics.dto.ReportConversionInformationResponse;
import com.adplatform.restApi.domain.statistics.dto.ReportInformationResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Seohyun Lee
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
            private AdGroup.Config config;
            private AdGroup.SystemConfig systemConfig;
            private AdGroup.Status status;
            private Integer campaignId;
            private String campaignName;
            private Integer scheduleStartDate;
            private Integer scheduleEndDate;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
            private ReportInformationResponse reportInformation;
            private ReportConversionInformationResponse reportConversionInformation;

            @QueryProjection
            public AdvertiserSearch(
                    Integer id,
                    String adTypeName,
                    String adGoalName,
                    String name,
                    AdGroup.Pacing pacing,
                    AdGroup.PacingType pacingType,
                    Long bidAmount,
                    AdGroup.BidStrategy bidStrategy,
                    Long dailyBudgetAmount,
                    AdGroup.Config config,
                    AdGroup.SystemConfig systemConfig,
                    AdGroup.Status status,
                    Integer campaignId,
                    String campaignName,
                    Integer scheduleStartDate,
                    Integer scheduleEndDate,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt,
                    ReportInformationResponse reportInformation,
                    ReportConversionInformationResponse reportConversionInformation) {
                this.id = id;
                this.adTypeName = adTypeName;
                this.adGoalName = adGoalName;
                this.name = name;
                this.pacing = pacing;
                this.pacingType = pacingType;
                this.bidAmount = bidAmount;
                this.bidStrategy = bidStrategy;
                this.dailyBudgetAmount = dailyBudgetAmount;
                this.config = config;
                this.systemConfig = systemConfig;
                this.status = status;
                this.campaignId = campaignId;
                this.campaignName = campaignName;
                this.scheduleStartDate = scheduleStartDate;
                this.scheduleEndDate = scheduleEndDate;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
                this.reportInformation = reportInformation;
                this.reportConversionInformation = reportConversionInformation;
            }
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
            private boolean fullDeviceDisplay;
            private boolean onlyWifiDisplay;
            private boolean allMedia;
            private boolean onlyAdult;
            private AdGroup.Config config;
            private AdGroup.SystemConfig systemConfig;
            private AdGroup.Status status;
        }
    }
}
