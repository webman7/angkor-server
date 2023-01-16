package com.adplatform.restApi.domain.report.dao.dashboard.mapper;

import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import com.adplatform.restApi.domain.report.dto.dashboard.ReportDashboardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportDashboardQueryMapper {

    List<ReportDashboardDto.Response.IndicatorColumn> indicatorColumn();
}
