package com.adplatform.restApi.domain.history.dto.adaccount.user;

import com.adplatform.restApi.domain.history.domain.adaccount.user.AdAccountUserInfoHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class AdAccountUserInfoHistoryMapper {
    public abstract AdAccountUserInfoHistory toEntity(AdAccountUserInfoHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
