package com.adplatform.restApi.domain.business.dto.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface BusinessAccountUserMapper {
    @Mapping(target = "businessAccount", source = "businessAccount")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "memberType", expression = "java(BusinessAccountUser.MemberType.MEMBER)")
    @Mapping(target = "accountingYN", expression = "java(BusinessAccountUser.AccountingYN.N)")
    @Mapping(target = "status", expression = "java(BusinessAccountUser.Status.N)")
    BusinessAccountUser toEntity(BusinessAccountUserDto.Request.SaveUser request, BusinessAccount businessAccount, User user);
}
