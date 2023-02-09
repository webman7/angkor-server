package com.adplatform.restApi.domain.agency.company.dao.mapper;

import com.adplatform.restApi.domain.agency.company.dto.AgencyCompanyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyCompanyQueryMapper {

    List<AgencyCompanyDto.Response.SpendSummary> walletSpendSummary(Integer companyId);
}
