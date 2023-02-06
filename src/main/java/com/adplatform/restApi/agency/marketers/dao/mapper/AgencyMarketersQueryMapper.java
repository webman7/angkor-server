package com.adplatform.restApi.agency.marketers.dao.mapper;

import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.agency.marketers.dto.AgencyMarketersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface AgencyMarketersQueryMapper {
    List<AgencyMarketersDto.Response.MarketersDetail> my(Integer loginUserNo);

    List<AgencyMarketersDto.Response.Search> search(
            @Param("request") AgencyMarketersDto.Request.Search request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AgencyMarketersDto.Request.Search request);

    List<AgencyMarketersDto.Response.SearchMarketers> searchMarketers(
            @Param("request") AgencyMarketersDto.Request.SearchMarketers request,
            @Param("pageable") Pageable pageable
    );

    long countSearchMarketers(@Param("request") AgencyMarketersDto.Request.SearchMarketers request);
}
