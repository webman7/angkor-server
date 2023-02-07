package com.adplatform.restApi.domain.advertiser.adgroup.dto.target;

import com.adplatform.restApi.global.constant.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public abstract class AdGroupDemographicTargetDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class FirstSave {
            @NotNull
            private boolean allAge;
            @NotNull
            private List<String> ages;
            @NotNull
            private boolean allGender;
            private Gender gender;
        }
    }

    public static abstract class Response {
        @NoArgsConstructor
        public static class Default extends Request.FirstSave {
        }
    }
}
