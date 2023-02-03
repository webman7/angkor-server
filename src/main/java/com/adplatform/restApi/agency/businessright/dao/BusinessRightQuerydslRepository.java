package com.adplatform.restApi.agency.businessright.dao;

import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;

import java.util.List;

public interface BusinessRightQuerydslRepository {

    List<BusinessRightDto.Response.BusinessRightDetail> getBusinessRight(Integer adAccountId, Integer companyId);
}
