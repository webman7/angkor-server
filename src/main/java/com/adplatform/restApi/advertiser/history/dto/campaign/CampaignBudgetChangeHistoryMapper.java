package com.adplatform.restApi.advertiser.history.dto.campaign;

import com.adplatform.restApi.advertiser.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public abstract class CampaignBudgetChangeHistoryMapper {

    @Mapping(target = "createdUserNo", source = "loginUserNo")
    public abstract CampaignBudgetChangeHistory toEntity(CampaignBudgetChangeHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
