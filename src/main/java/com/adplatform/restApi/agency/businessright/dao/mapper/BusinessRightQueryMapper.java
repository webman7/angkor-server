package com.adplatform.restApi.agency.businessright.dao.mapper;

import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BusinessRightQueryMapper {

    List<BusinessRightDto.Response.Statistics> statistics(BusinessRightDto.Request.Statistics request, Integer companyId);

}
