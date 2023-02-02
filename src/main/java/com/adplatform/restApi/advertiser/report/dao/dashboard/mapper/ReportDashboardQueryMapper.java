package com.adplatform.restApi.advertiser.report.dao.dashboard.mapper;

import com.adplatform.restApi.advertiser.report.dto.dashboard.ReportDashboardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportDashboardQueryMapper {

    List<ReportDashboardDto.Response.IndicatorColumn> indicatorColumn();
}
