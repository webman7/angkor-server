package com.adplatform.restApi.domain.dashboard.dao.dashboard.mapper;

import com.adplatform.restApi.domain.dashboard.dto.DashboardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DashboardQueryMapper {
    List<DashboardDto.Response.DashboardChart> adAccountsDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    DashboardDto.Response.AdAccountDashboardCost adAccountsDashboardCost(
            @Param("request") DashboardDto.Request.AdAccountDashboard request);

    List<DashboardDto.Response.DashboardChart> campaignsDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> campaignByIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request, Integer campaignId);

    List<DashboardDto.Response.DashboardChart> adGroupsDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> adGroupByIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request, Integer adGroupId);

    List<DashboardDto.Response.DashboardChart> creativesDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> creativeByIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request);
}
