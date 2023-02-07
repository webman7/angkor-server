package com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.advertiser.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface AdGroupEventMapper {
    @Mapping(target = "campaign", source = "campaign")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "dailyBudgetAmount", source = "request.dailyBudgetAmount")
    @Mapping(target = "budgetAmount", source = "request.budgetAmount")
    AdGroupSavedEvent toEvent(AdGroupDto.Request.FirstSave request, Campaign campaign);
}
