package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.adplatform.restApi.domain.statistics.domain.sale.SaleAmountDaily;
import com.adplatform.restApi.domain.statistics.domain.sale.SaleDetailAmountDaily;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class SaleAmountMapper {
    public abstract SaleAmountDaily toEntity(SaleAmountDto.Request.Save SaveDto);

    public abstract SaleDetailAmountDaily toEntityDetail(SaleAmountDto.Request.Save SaveDto);
}
