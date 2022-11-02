package com.adplatform.restApi.domain.campaign.dto;

import com.adplatform.restApi.domain.campaign.domain.AdTypeAndGoal;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface CampaignMapper {
    @Mapping(target = "adTypeAndGoal", source = "adTypeAndGoal")
    @Mapping(target = "config", expression = "java(Campaign.Config.ON)")
    @Mapping(target = "systemConfig", expression = "java(Campaign.SystemConfig.ON)")
    @Mapping(target = "status", expression = "java(Campaign.Status.READY)")
    Campaign toEntity(CampaignDto.Request.Save dto, AdTypeAndGoal adTypeAndGoal);
}
