package com.adplatform.restApi.domain.adaccount.dto.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface AdAccountUserMapper {
    @Mapping(target = "adAccount", source = "adAccount")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", expression = "java(AdAccountUser.Status.Y)")
    AdAccountUser toEntityInvite(AdAccountUserDto.Request.SaveUser request, AdAccount adAccount, User user);
}
