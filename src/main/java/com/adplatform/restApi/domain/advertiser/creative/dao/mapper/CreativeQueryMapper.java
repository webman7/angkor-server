package com.adplatform.restApi.domain.advertiser.creative.dao.mapper;

import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
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

    List<CreativeDto.Response.ReviewSearch> reviewSearch(
            @Param("pageable") Pageable pageable, @Param("request") CreativeDto.Request.ReviewSearch request, @Param("loginUserNo") Integer loginUserNo
    );
    long countReviewSearch(@Param("request") CreativeDto.Request.ReviewSearch request, @Param("loginUserNo") Integer loginUserNo);


    List<CreativeDto.Response.Category> creativeMediaCategoryList(@Param("creativeId") Integer creativeId);

    void insertCreativeMediaCategory(@Param("creativeId") Integer creativeId, @Param("categoryId") Integer categoryId, @Param("mediaId") Integer mediaId);
}
