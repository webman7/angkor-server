package com.adplatform.restApi.domain.history.dto.user;

import com.adplatform.restApi.domain.history.domain.UserHistory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public abstract class UserHistoryMapper {
    @Mapping(target = "userNo", source = "SaveDto.userNo")
    @Mapping(target = "userId", source = "SaveDto.userId")
    @Mapping(target = "name", source = "SaveDto.userName")
    @Mapping(target = "phone", source = "SaveDto.phone")
    @Mapping(target = "active", source = "SaveDto.active")
    @Mapping(target = "pwdWrongCnt", source = "SaveDto.pwdWrongCnt")
    @Mapping(target = "pwdUpdatedAt", source = "SaveDto.pwdUpdatedAt")
    @Mapping(target = "statusChangedUserNo", source = "SaveDto.statusChangedUserNo")
    @Mapping(target = "statusChangedAt", source = "SaveDto.statusChangedAt")
    @Mapping(target = "regUserNo", source = "SaveDto.regUserNo")
    @Mapping(target = "createdAt", source = "SaveDto.createdAt")
    @Mapping(target = "updUserNo", source = "SaveDto.updUserNo")
    @Mapping(target = "updatedAt", source = "SaveDto.updatedAt")
    public abstract UserHistory toEntity(UserHistoryDto.Request.Save SaveDto);

}
