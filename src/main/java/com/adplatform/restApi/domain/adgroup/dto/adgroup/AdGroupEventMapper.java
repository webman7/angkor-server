package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adgroup.event.AdGroupSavedEvent;
import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface AdGroupEventMapper {
    @Mapping(target = "campaign", source = "campaign")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "dailyBudgetAmount", source = "request.dailyBudgetAmount")
    AdGroupSavedEvent toEvent(AdGroupDto.Request.FirstSave request, Campaign campaign);
}
