package com.adplatform.restApi.domain.company.dto.user;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUserTransfer;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface MediaCompanyUserTransferMapper {
    @Mapping(target = "companyId", source = "companyId")
    @Mapping(target = "userNo", source = "userNo")
    @Mapping(target = "transferUserNo", source = "transferUserNo")
    MediaCompanyUserTransfer toEntity(Integer companyId, Integer userNo, Integer transferUserNo);
}
