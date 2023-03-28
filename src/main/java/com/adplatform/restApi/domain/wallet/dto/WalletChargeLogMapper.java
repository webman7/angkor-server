package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletChargeLog;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface WalletChargeLogMapper {

    WalletChargeLog toEntity(WalletDto.Request.SaveCredit request, Integer loginUserNo);
}
