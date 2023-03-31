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

    void insertAdGroupMedia(@Param("adGroupId") Integer adGroupId, @Param("categoryId") Integer categoryId, @Param("mediaId") Integer mediaId);

    List<AdGroupDto.Response.Placement> adGroupPlacementList(@Param("adGroupId") Integer adGroupId);

    List<AdGroupDto.Response.Category> adGroupCategoryList(@Param("adGroupId") Integer adGroupId);

    List<AdGroupDto.Response.CategoryMedia> adGroupCategoryMediaList(@Param("adGroupId") Integer adGroupId);
    List<AdGroupDto.Response.PlacementCategory> adGroupPlacementCategoryList(@Param("adGroupId") Integer adGroupId, @Param("placementId") Integer placementId);
    List<AdGroupDto.Response.PlacementMedia> adGroupPlacementMediaList(@Param("adGroupId") Integer adGroupId, @Param("placementId") Integer placementId, @Param("categoryId") Integer categoryId);
}
