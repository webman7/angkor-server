package com.adplatform.restApi.domain.adgroup.dto.target;

import com.adplatform.restApi.global.constant.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class AdGroupDemographicTargetDto {
    public static class Request {
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
}
