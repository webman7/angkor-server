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
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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

    @Mapping(target = "adGroupId", source = "adGroup.id")
    @Mapping(target = "media", source = "adGroup.media", qualifiedByName = "mapMedia")
    @Mapping(target = "devices", source = "adGroup.devices", qualifiedByName = "mapDevices")
    AdGroupDto.Response.Detail toDetailResponse(AdGroup adGroup);

    @Named("mapMedia")
    default List<String> mapMedia(Collection<Media> media) {
        return media.stream().map(Media::getName).collect(Collectors.toList());
    }

    @Named("mapDevices")
    default List<String> mapDevice(Collection<Device> devices) {
        return devices.stream().map(Device::getName).collect(Collectors.toList());
    }
}
