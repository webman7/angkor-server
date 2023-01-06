package com.adplatform.restApi.domain.report.dto.custom;

import com.querydsl.core.annotations.QueryProjection;
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
            private String reportLevel;
            private List<String> configs;
            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;
        }

        @Getter
        @Setter
        public static class Update {
            @NotNull
            private Integer id;
            @NotNull
            private Integer adAccountId;
            private String name;
            private String reportLevel;
            private List<String> configs;
            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private Integer adAccountId;
            private String name;
            private String reportLevel;
            private List<String> configs;
            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Default {
            private Integer id;
            private Integer adAccountId;
            private String name;
            private String reportLevel;
            private List<String> configs;
            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;

            @QueryProjection
            public Default(Integer id, Integer adAccountId, String name, String reportLevel, List<String> configs, List<String> indicators, Integer startDate, Integer endDate) {
                this.id = id;
                this.adAccountId = adAccountId;
                this.name = name;
                this.reportLevel = reportLevel;
                this.configs = configs;
                this.indicators = indicators;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }


    }
}
