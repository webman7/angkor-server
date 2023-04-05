package com.adplatform.restApi.domain.company.dto.user;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.AdminUser;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface AdminUserMapper {
    @Mapping(target = "company", source = "company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", expression = "java(AdminUser.Status.Y)")
    AdminUser toEntity(AdminUserDto.Request.SaveUser request, Company company, User user);

    @Mapping(target = "company", source = "company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", expression = "java(AdminUser.Status.Y)")
    AdminUser toEntityInvite(AdminUserDto.Request.SaveUser request, Company company, User user);
}

