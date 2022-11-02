package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
}
