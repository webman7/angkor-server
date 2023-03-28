package com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.mapper;

import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.advertiser.campaign.dto.AdvertiserSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author junny
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

    void insertAdGroupMedia(Integer adGroupId, Integer categoryId, Integer mediaId);

    List<AdGroupDto.Response.Placement> adGroupPlacementList(Integer adGroupId);

    List<AdGroupDto.Response.Category> adGroupCategoryList(Integer adGroupId);

    List<AdGroupDto.Response.CategoryMedia> adGroupCategoryMediaList(Integer adGroupId);
    List<AdGroupDto.Response.PlacementCategory> adGroupPlacementCategoryList(Integer adGroupId, Integer placementId);
    List<AdGroupDto.Response.PlacementMedia> adGroupPlacementMediaList(Integer adGroupId, Integer placementId, Integer categoryId);
}
