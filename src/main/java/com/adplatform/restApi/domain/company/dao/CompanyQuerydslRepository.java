package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQuerydslRepository {
    Page<CompanyDto.Response.Page> search(Pageable pageable, CompanyDto.Request.Search searchRequest);
}
