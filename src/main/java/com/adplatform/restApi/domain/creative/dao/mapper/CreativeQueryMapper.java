package com.adplatform.restApi.domain.creative.dao.mapper;

import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.dashboard.dto.DashboardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface CreativeQueryMapper {
    List<CreativeDto.Response.Default> search(
            @Param("request") AdvertiserSearchRequest request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AdvertiserSearchRequest request);

    List<CreativeDto.Response.Default> searchCreativeList(
            @Param("request") AdvertiserSearchRequest request,
            @Param("pageable") Pageable pageable
    );
}
