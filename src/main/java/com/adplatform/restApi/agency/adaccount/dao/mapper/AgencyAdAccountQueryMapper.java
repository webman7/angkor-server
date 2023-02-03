package com.adplatform.restApi.agency.adaccount.dao.mapper;

import com.adplatform.restApi.agency.adaccount.dto.AgencyAdAccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface AgencyAdAccountQueryMapper {

    List<AgencyAdAccountDto.Response.Search> search(
            @Param("request") AgencyAdAccountDto.Request.Search request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AgencyAdAccountDto.Request.Search request);
}
