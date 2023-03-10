package com.adplatform.restApi.domain.agency.businessright.dto;

import com.adplatform.restApi.domain.agency.businessright.domain.BusinessRightRequest;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface BusinessRightRequestMapper {
//    @Mapping(target = "status", expression = "java(BusinessRightRequest.Status.REQUESTED)")
//    BusinessRightRequest toEntity(BusinessRightDto.Request.SaveRequest request);
}
