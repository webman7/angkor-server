package com.adplatform.restApi.domain.adgroup.event;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetDto;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AdGroupSavedEvent {
    private final Campaign campaign;
    private final AdGroupDemographicTargetDto.Request.FirstSave demographicTarget;
    private final AdGroupScheduleDto.Request.FirstSave adGroupSchedule;
    private final List<String> media;
    private final List<String> devices;
    private final String name;
    private final AdGroup.Pacing pacing;
    private final AdGroup.PacingType pacingType;
    private final Long bidAmount;
    private final AdGroup.BidStrategy bidStrategy;
    private final Long dailyBudgetAmount;
    private final boolean fullDeviceDisplay;
    private final boolean onlyWifiDisplay;
    private final boolean allMedia;
    private final boolean onlyAdult;

    @Builder
    public AdGroupSavedEvent(
            Campaign campaign,
            AdGroupDemographicTargetDto.Request.FirstSave demographicTarget,
            AdGroupScheduleDto.Request.FirstSave adGroupSchedule,
            List<String> media,
            List<String> devices,
            String name,
            AdGroup.Pacing pacing,
            AdGroup.PacingType pacingType,
            Long bidAmount,
            AdGroup.BidStrategy bidStrategy,
            Long dailyBudgetAmount,
            boolean fullDeviceDisplay,
            boolean onlyWifiDisplay,
            boolean allMedia,
            boolean onlyAdult) {
        this.campaign = campaign;
        this.demographicTarget = demographicTarget;
        this.adGroupSchedule = adGroupSchedule;
        this.media = media;
        this.devices = devices;
        this.name = name;
        this.pacing = pacing;
        this.pacingType = pacingType;
        this.bidAmount = bidAmount;
        this.bidStrategy = bidStrategy;
        this.dailyBudgetAmount = dailyBudgetAmount;
        this.fullDeviceDisplay = fullDeviceDisplay;
        this.onlyWifiDisplay = onlyWifiDisplay;
        this.allMedia = allMedia;
        this.onlyAdult = onlyAdult;
    }
}
