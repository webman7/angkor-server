package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.domain.statistics.domain.taxbill.AdAccountTaxBillMonthly;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class TaxBillMapper {
//    public abstract AdAccountTaxBillMonthly toEntityTaxBillMonthly(TaxBillDto.Request.Save SaveDto);
}
