package com.adplatform.restApi.domain.advertiser.campaign.dto;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.advertiser.campaign.domain.AdTypeAndGoal;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface CampaignMapper {
    @Mapping(target = "adTypeAndGoal", source = "adTypeAndGoal")
    @Mapping(target = "adAccount", source = "adAccount")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "config", expression = "java(Campaign.Config.ON)")
    @Mapping(target = "systemConfig", expression = "java(Campaign.SystemConfig.ON)")
    @Mapping(target = "status", expression = "java(Campaign.Status.READY)")
    Campaign toEntity(CampaignDto.Request.Save dto, AdTypeAndGoal adTypeAndGoal, AdAccount adAccount);
}
