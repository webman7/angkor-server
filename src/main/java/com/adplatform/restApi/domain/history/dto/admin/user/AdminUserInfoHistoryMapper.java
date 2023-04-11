package com.adplatform.restApi.domain.history.dto.admin.user;

import com.adplatform.restApi.domain.history.domain.admin.user.AdminUserInfoHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class AdminUserInfoHistoryMapper {
    public abstract AdminUserInfoHistory toEntity(AdminUserInfoHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
