package com.adplatform.restApi.domain.business.dto.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author junny
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface BusinessAccountMapper {
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "creditLimit", ignore = true)
    @Mapping(target = "prePayment", expression = "java(true)")
    @Mapping(target = "type", expression = "java(BusinessAccount.PaymentType.prepayment)")
    @Mapping(target = "config", expression = "java(BusinessAccount.Config.ON)")
    BusinessAccount toEntity(BusinessAccountDto.Request.Save request, Company company);
}
