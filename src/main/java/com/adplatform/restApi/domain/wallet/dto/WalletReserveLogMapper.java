package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletReserveLog;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WalletReserveLogMapper {

    WalletReserveLog toEntity(WalletDto.Request.SaveWalletReserveLog request, Integer loginUserNo);
}
