package com.adplatform.restApi.agency.company.dao.mapper;

import com.adplatform.restApi.agency.company.dto.AgencyCompanyDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgencyCompanyQueryMapper {

    AgencyCompanyDto.Response.SpendSummary walletSpendSummary(Integer loginUserNo);
}
