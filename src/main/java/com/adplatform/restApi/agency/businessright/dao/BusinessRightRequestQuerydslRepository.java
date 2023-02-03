package com.adplatform.restApi.agency.businessright.dao;

import com.adplatform.restApi.agency.businessright.dto.BusinessRightDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusinessRightRequestQuerydslRepository {

    Page<BusinessRightDto.Response.Search> search(Pageable pageable, BusinessRightDto.Request.Search request);
}
