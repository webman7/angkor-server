package com.adplatform.restApi.advertiser.adgroup.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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

    public static abstract class Response {
        @NoArgsConstructor
        public static class Default extends Request.FirstSave {
        }
    }
}
