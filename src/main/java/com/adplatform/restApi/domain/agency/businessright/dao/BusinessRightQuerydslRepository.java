package com.adplatform.restApi.domain.agency.businessright.dao;

import com.adplatform.restApi.domain.agency.businessright.dto.BusinessRightDto;

import java.util.List;

public interface BusinessRightQuerydslRepository {

    List<BusinessRightDto.Response.BusinessRightDetail> getBusinessRight(Integer adAccountId, Integer companyId);
}