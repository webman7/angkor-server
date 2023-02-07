package com.adplatform.restApi.domain.history.dto.campaign;

import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class UserPasswordChangeHistoryMapper {
    public abstract UserPasswordChangeHistory toEntity(UserPasswordChangeHistoryDto.Request.Save SaveDto);
}
