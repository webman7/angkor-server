package com.adplatform.restApi.domain.history.dto;

import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class AdminStopHistoryMapper {
    public abstract AdminStopHistory toEntity(AdminStopHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
