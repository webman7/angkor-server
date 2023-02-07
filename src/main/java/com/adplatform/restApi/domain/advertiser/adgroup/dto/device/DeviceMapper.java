package com.adplatform.restApi.domain.advertiser.adgroup.dto.device;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.Device;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface DeviceMapper {
    List<DeviceDto.Response.Default> toDefaultResponse(List<Device> devices);
}
