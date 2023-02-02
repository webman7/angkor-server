package com.adplatform.restApi.advertiser.report.dao.custom.mapper;

import com.adplatform.restApi.advertiser.report.dto.custom.ReportCustomDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface ReportCustomQueryMapper {

    List<ReportCustomDto.Response.Page> adAccountsDailyTotal(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countAdAccountsDailyTotal(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> adAccountsDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countAdAccountsDaily(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> campaignsDailyTotal(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countCampaignsDailyTotal(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> campaignsDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countCampaignsDaily(@Param("request") ReportCustomDto.Request.Report request);


    List<ReportCustomDto.Response.Page> adGroupsDailyTotal(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countAdGroupsDailyTotal(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> adGroupsDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countAdGroupsDaily(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> creativesDailyTotal(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countCreativesDailyTotal(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> creativesDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    long countCreativesDaily(@Param("request") ReportCustomDto.Request.Report request);

    List<ReportCustomDto.Response.Page> adAccountCampaignsDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    List<ReportCustomDto.Response.Page> adAccountAdGroupsDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );

    List<ReportCustomDto.Response.Page> adAccountCreativesDaily(
            @Param("request") ReportCustomDto.Request.Report request,
            @Param("pageable") Pageable pageable
    );
}
