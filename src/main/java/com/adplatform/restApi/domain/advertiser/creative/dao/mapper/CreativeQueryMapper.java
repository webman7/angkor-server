package com.adplatform.restApi.domain.advertiser.creative.dao.mapper;

import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
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

    List<CreativeDto.Response.Category> creativeMediaCategoryList(Integer creativeId);

    void insertCreativeMediaCategory(Integer creativeId, Integer categoryId, Integer mediaId);
}
