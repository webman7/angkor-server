package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetDto;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public abstract class AdGroupDto {
    public static abstract class Request {
        @Getter
        @Setter
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
        public static class Default {
            private Integer id;
            private String name;
            private AdGroup.Pacing pacing;
            private AdGroup.PacingType pacingType;
            private Long bidAmount;
            private AdGroup.BidStrategy bidStrategy;
            private Long dailyBudgetAmount;
            private AdGroup.Config config;
            private AdGroup.SystemConfig systemConfig;
            private Campaign.Status status;
            private Integer campaignId;
            private String campaignName;
            private Integer scheduleStartDate;
            private Integer scheduleEndDate;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;

            @QueryProjection
            public Default(
                    Integer id,
                    String name,
                    AdGroup.Pacing pacing,
                    AdGroup.PacingType pacingType,
                    Long bidAmount,
                    AdGroup.BidStrategy bidStrategy,
                    Long dailyBudgetAmount,
                    AdGroup.Config config,
                    AdGroup.SystemConfig systemConfig,
                    Campaign.Status status,
                    Integer campaignId,
                    String campaignName,
                    Integer scheduleStartDate,
                    Integer scheduleEndDate,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
                this.id = id;
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
            }
        }
    }
}
