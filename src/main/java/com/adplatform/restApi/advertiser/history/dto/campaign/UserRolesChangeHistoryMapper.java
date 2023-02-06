package com.adplatform.restApi.advertiser.history.dto.campaign;

import com.adplatform.restApi.advertiser.history.domain.UserRolesChangeHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class UserRolesChangeHistoryMapper {

    public abstract UserRolesChangeHistory toEntity(UserRolesChangeHistoryDto.Request.Save SaveDto);
}
