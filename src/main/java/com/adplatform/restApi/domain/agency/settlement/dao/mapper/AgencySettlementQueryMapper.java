package com.adplatform.restApi.domain.agency.settlement.dao.mapper;

import com.adplatform.restApi.domain.agency.settlement.dto.AgencySettlementDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface AgencySettlementQueryMapper {

    AgencySettlementDto.Response.AmountSum amountSum(AgencySettlementDto.Request.AmountSum request);

    List<AgencySettlementDto.Response.Search> search(
            @Param("request") AgencySettlementDto.Request.Search request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") AgencySettlementDto.Request.Search request);
}
