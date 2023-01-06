package com.adplatform.restApi.domain.report.dto.custom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class ReportCustomDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @NotNull
            private Integer adAccountId;
            private String name;
            private List<String> reportLevel;
            private List<String> config;
            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;
        }
    }

    public static abstract class Response {
        @NoArgsConstructor
        public static class Default extends ReportCustomDto.Request.Save {
        }
    }
}
