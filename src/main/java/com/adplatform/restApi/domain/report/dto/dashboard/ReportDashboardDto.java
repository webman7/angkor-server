package com.adplatform.restApi.domain.report.dto.dashboard;

import com.adplatform.restApi.domain.campaign.dto.AdTypeAndGoalDto;
import com.adplatform.restApi.domain.statistics.dto.ReportDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
