package com.adplatform.restApi.domain.adaccount.dao.adaccount.mapper;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdAccountQueryMapper {
    List<AdAccountDto.Response.AdAccountDashboardChart> adAccountDashboardChart(
            @Param("request") AdAccountDto.Request.AdAccountDashboardChart request);

    AdAccountDto.Response.AdAccountDashboardCost adAccountDashboardCost(
            @Param("request") AdAccountDto.Request.AdAccountDashboardCost request);

}
