package com.adplatform.restApi.advertiser.adaccount.dto.adaccount;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdAccountBusinessRightDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveRequest {
            @NotNull
            private Integer adAccountId;
            @NotBlank
            private Integer companyId;
            private Integer requestCompanyId;
        }

        @Getter
        @Setter
        public static class SaveStatus {
            @NotNull
            private Integer id;
            @NotBlank
            private String status;
        }

        @Getter
        @Setter
        public static class Save {
            @NotNull
            private Integer adAccountId;
            @NotBlank
            private Integer companyId;
            private Integer startDate;
            private Integer endDate;
        }
    }

    public static abstract class Response {

    }
}
