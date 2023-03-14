package com.adplatform.restApi.domain.history.dto.mediaCompany.user;

import com.adplatform.restApi.domain.history.domain.mediaCompany.user.MediaCompanyUserInfoHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class MediaCompanyUserInfoHistoryMapper {
    public abstract MediaCompanyUserInfoHistory toEntity(MediaCompanyUserInfoHistoryDto.Request.Save SaveDto, Integer loginUserNo);
}
