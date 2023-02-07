package com.adplatform.restApi.history.dto.campaign;

import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.history.domain.UserLoginHistory;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class UserLoginHistoryMapper {
    public abstract UserLoginHistory toEntity(UserLoginHistoryDto.Request.Save SaveDto);
}
