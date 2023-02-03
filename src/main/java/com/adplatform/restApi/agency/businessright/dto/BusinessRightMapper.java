package com.adplatform.restApi.agency.businessright.dto;

import com.adplatform.restApi.agency.businessright.domain.BusinessRight;
import com.adplatform.restApi.agency.businessright.domain.BusinessRightRequest;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface BusinessRightMapper {
    BusinessRight toEntity(BusinessRightDto.Request.Save request);
}
