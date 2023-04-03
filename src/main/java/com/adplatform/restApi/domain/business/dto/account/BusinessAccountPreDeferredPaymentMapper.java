package com.adplatform.restApi.domain.business.dto.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountPreDeferredPayment;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface BusinessAccountPreDeferredPaymentMapper {

    @Mapping(target = "businessAccountId", source = "businessAccountId")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "prePayment", expression = "java(true)")
    BusinessAccountPreDeferredPayment toEntity(Integer businessAccountId, Integer startDate);
}
