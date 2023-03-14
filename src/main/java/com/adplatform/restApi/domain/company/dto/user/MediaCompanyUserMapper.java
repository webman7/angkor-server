package com.adplatform.restApi.domain.company.dto.user;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(config = BaseMapperConfig.class)
public interface MediaCompanyUserMapper {
    @Mapping(target = "company", source = "company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "accountingYN", expression = "java(MediaCompanyUser.AccountingYN.Y)")
    @Mapping(target = "status", expression = "java(MediaCompanyUser.Status.Y)")
    MediaCompanyUser toEntity(MediaCompanyUserDto.Request.SaveUser request, Company company, User user);

    @Mapping(target = "company", source = "company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "accountingYN", expression = "java(MediaCompanyUser.AccountingYN.N)")
    @Mapping(target = "status", expression = "java(MediaCompanyUser.Status.Y)")
    MediaCompanyUser toEntityInvite(MediaCompanyUserDto.Request.SaveUser request, Company company, User user);
}
