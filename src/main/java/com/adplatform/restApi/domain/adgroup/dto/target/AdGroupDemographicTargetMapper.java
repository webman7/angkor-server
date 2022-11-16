package com.adplatform.restApi.domain.adgroup.dto.target;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupDemographicTarget;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface AdGroupDemographicTargetMapper {
    @Mapping(target = "adGroup", ignore = true)
    AdGroupDemographicTarget toEntity(AdGroupDemographicTargetDto.Request.FirstSave dto);

    AdGroupDemographicTargetDto.Response.Default toDefaultResponse(AdGroupDemographicTarget demographicTarget);
}
