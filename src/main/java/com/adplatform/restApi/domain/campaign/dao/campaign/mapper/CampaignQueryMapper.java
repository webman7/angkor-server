package com.adplatform.restApi.domain.campaign.dao.campaign.mapper;

import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.campaign.dto.CampaignDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper
public interface CampaignQueryMapper {
    List<CampaignDto.Response.Page> search(
            @Param("request") AdvertiserSearchRequest request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AdvertiserSearchRequest request);

    List<CampaignDto.Response.Page> searchCampaignSelectQuery(
            @Param("request") AdvertiserSearchRequest request);

}
