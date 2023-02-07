package com.adplatform.restApi.adaccount.dto.adaccount;

import com.adplatform.restApi.adaccount.domain.AdAccount;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface AdAccountMapper {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "platformType", expression = "java(AdAccount.PlatformType.AD)")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "businessRight", expression = "java(false)")
    @Mapping(target = "requestBusinessRight", expression = "java(true)")
    @Mapping(target = "creditLimit", ignore = true)
    @Mapping(target = "preDeferredPayment", expression = "java(true)")
    @Mapping(target = "config", expression = "java(AdAccount.Config.ON)")
    @Mapping(target = "adminStop", expression = "java(false)")
    @Mapping(target = "outOfBalance", expression = "java(true)")
    AdAccount toEntity(AdAccountDto.Request.Save request, User user);
}
