package com.adplatform.restApi.domain.adgroup.dto.schedule;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public abstract class AdGroupScheduleDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class FirstSave {
            @NotNull
            private Integer startDate;
            private Integer endDate;
            @NotNull
            private boolean lateNightTargeting;
            @NotNull
            private boolean hasDetailTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> mondayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> tuesdayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> wednesdayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> thursdayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> fridayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> saturdayTime;
            @NotNull
            @Size(min = 24, max = 24)
            private List<Boolean> sundayTime;
        }
    }
}
