package com.adplatform.restApi.domain.history.dto.user;

import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public abstract class UserPasswordChangeHistoryMapper {
    @Mapping(target = "status", expression = "java(UserPasswordChangeHistory.Status.READY)")
    public abstract UserPasswordChangeHistory toEntity(UserPasswordChangeHistoryDto.Request.Save SaveDto);
}
