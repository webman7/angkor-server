package com.adplatform.restApi.domain.history.dto.business.user;

import com.adplatform.restApi.domain.history.domain.business.user.BusinessAccountUserInfoHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public abstract class BusinessAccountUserInfoHistoryMapper {
    public abstract BusinessAccountUserInfoHistory toEntity(BusinessAccountUserInfoHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
