package com.adplatform.restApi.domain.business.dto.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUserTransfer;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface BusinessAccountUserTransferMapper {
    @Mapping(target = "businessAccountId", source = "businessAccountId")
    @Mapping(target = "userNo", source = "userNo")
    @Mapping(target = "transferUserNo", source = "transferUserNo")
    BusinessAccountUserTransfer toEntity(Integer businessAccountId, Integer userNo, Integer transferUserNo);
}
