package com.adplatform.restApi.agency.businessright.dao.mapper;

import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessRightQueryMapper {

    BusinessRightDto.Response.Statistics statistics(BusinessRightDto.Request.Statistics request, Integer companyId);

}
