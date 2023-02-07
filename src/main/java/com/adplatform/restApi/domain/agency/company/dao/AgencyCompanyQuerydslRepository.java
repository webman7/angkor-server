package com.adplatform.restApi.domain.agency.company.dao;

import com.adplatform.restApi.domain.agency.company.dto.AgencyCompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgencyCompanyQuerydslRepository {

    Page<AgencyCompanyDto.Response.SearchForAdmin> searchForAdmin(Pageable pageable, AgencyCompanyDto.Request.Search request, Integer companyId);

    List<AgencyCompanyDto.Response.SearchForAdmin> searchForAdmin(AgencyCompanyDto.Request.Search request, Integer companyId);
}
