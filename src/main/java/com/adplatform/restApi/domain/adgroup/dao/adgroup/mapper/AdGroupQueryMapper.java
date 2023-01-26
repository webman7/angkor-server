package com.adplatform.restApi.domain.adgroup.dao.adgroup.mapper;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.dashboard.dto.DashboardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper
public interface AdGroupQueryMapper {
    List<AdGroupDto.Response.AdvertiserSearch> search(
            @Param("request") AdvertiserSearchRequest request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AdvertiserSearchRequest request);

    List<AdGroupDto.Response.AdvertiserSearch> searchAdGroupList(
            @Param("request") AdvertiserSearchRequest request,
            @Param("pageable") Pageable pageable
    );
}
