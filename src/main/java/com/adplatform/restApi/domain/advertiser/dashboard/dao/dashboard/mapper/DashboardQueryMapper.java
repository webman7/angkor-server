package com.adplatform.restApi.domain.advertiser.dashboard.dao.dashboard.mapper;

import com.adplatform.restApi.domain.advertiser.dashboard.dto.DashboardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DashboardQueryMapper {
    List<DashboardDto.Response.DashboardChart> adAccountsDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    DashboardDto.Response.AdAccountDashboardCost adAccountsDashboardCost(
            @Param("request") DashboardDto.Request.AdAccountDashboard request);

    List<DashboardDto.Response.DashboardChart> totalDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> byIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request, @Param("campaignId") Integer campaignId, @Param("adGroupId") Integer adGroupId, @Param("creativeId") Integer creativeId);

    List<DashboardDto.Response.DashboardChart> adGroupsDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> adGroupByIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request, @Param("adGroupId") Integer adGroupId);

    List<DashboardDto.Response.DashboardChart> creativesDashboardChart(
            @Param("request") DashboardDto.Request.TotalDashboardChart request);

    List<DashboardDto.Response.DashboardChart> creativeByIdDashboardChart(
            @Param("request") DashboardDto.Request.DashboardChart request, @Param("creativeId") Integer creativeId);
}
