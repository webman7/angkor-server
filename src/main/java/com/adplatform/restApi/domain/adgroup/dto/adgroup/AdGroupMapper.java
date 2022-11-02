package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.domain.Device;
import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleMapper;
import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetMapper;
import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        config = BaseMapperConfig.class,
        uses = {AdGroupDemographicTargetMapper.class, AdGroupScheduleMapper.class},
        imports = {Campaign.class}
)
public interface AdGroupMapper {
    @Mapping(target = "media", source = "media")
    @Mapping(target = "devices", source = "devices")
    @Mapping(target = "config", expression = "java(AdGroup.Config.ON)")
    @Mapping(target = "systemConfig", expression = "java(AdGroup.SystemConfig.ON)")
    @Mapping(target = "status", expression = "java(Campaign.Status.READY)")
    AdGroup toEntity(AdGroupSavedEvent event, List<Media> media, List<Device> devices);
}
