package com.adplatform.restApi.advertiser.statistics.dto;

import com.adplatform.restApi.advertiser.statistics.domain.sale.SaleAmountDaily;
import com.adplatform.restApi.advertiser.statistics.domain.sale.SaleDetailAmountDaily;
import com.adplatform.restApi.advertiser.statistics.domain.sale.SaleRemainAmountDaily;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class SaleAmountMapper {
    public abstract SaleAmountDaily toEntity(SaleAmountDto.Request.Save SaveDto);

    public abstract SaleRemainAmountDaily toEntityRemain(SaleAmountDto.Request.Save SaveDto);

    public abstract SaleDetailAmountDaily toEntityDetail(SaleAmountDto.Request.Save SaveDto);
}
