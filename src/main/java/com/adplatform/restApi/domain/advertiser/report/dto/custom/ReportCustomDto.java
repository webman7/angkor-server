package com.adplatform.restApi.domain.advertiser.report.dto.custom;

import com.adplatform.restApi.domain.advertiser.campaign.dto.AdTypeAndGoalDto;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
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

        @Getter
        @Setter
        public static class Report {
            private Integer id;
            private Integer adAccountId;
            private Integer campaignId;
            private Integer adGroupId;
            private Integer creativeId;
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

        @Getter
        @Setter
        public static class Page {
            private Integer id;
            private Integer adAccountId;
            private String adAccountName;
            private AdTypeAndGoalDto adTypeAndGoal;
            private Integer campaignId;
            private String campaignName;
            private String campaignUserConfig;
            private Integer adGroupId;
            private String adGroupName;
            private String adGroupUserConfig;
            private Integer creativeId;
            private String creativeName;
            private String creativeUserConfig;
            private Integer representativeId;
//            private List<String> configs;
//            private List<String> indicators;
            private Integer startDate;
            private Integer endDate;
            private ReportDto.Response report;
        }


    }
}
