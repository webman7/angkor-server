package com.adplatform.restApi.domain.adaccount.dto.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface AdAccountMapper {
    @Mapping(target = "businessAccount", source = "businessAccount")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "config", expression = "java(AdAccount.Config.ON)")
    @Mapping(target = "adminStop", expression = "java(false)")
    @Mapping(target = "outOfBalance", expression = "java(true)")
    AdAccount toEntity(AdAccountDto.Request.Save request, BusinessAccount businessAccount);
}
