package com.adplatform.restApi.advertiser.adgroup.dto.target;

import com.adplatform.restApi.advertiser.adgroup.domain.AdGroupDemographicTarget;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface AdGroupDemographicTargetMapper {
    @Mapping(target = "adGroup", ignore = true)
    AdGroupDemographicTarget toEntity(AdGroupDemographicTargetDto.Request.FirstSave dto);

    AdGroupDemographicTargetDto.Response.Default toDefaultResponse(AdGroupDemographicTarget demographicTarget);
}
