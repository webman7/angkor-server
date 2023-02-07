package com.adplatform.restApi.domain.advertiser.report.dto.dashboard;

import lombok.Getter;
import lombok.Setter;

public abstract class ReportDashboardDto {
    public static abstract class Response {

        @Getter
        @Setter
        public static class IndicatorColumn {
            private String columnIndex;
            private String columnName;
        }


    }
}
