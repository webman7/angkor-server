package com.adplatform.restApi.domain.agency.taxbill.dao.mapper;

import com.adplatform.restApi.domain.agency.taxbill.dto.AgencyTaxBillDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface AgencyTaxBillQueryMapper {

    List<AgencyTaxBillDto.Response.Search> search(
            @Param("request") AgencyTaxBillDto.Request.Search request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AgencyTaxBillDto.Request.Search request);
}
