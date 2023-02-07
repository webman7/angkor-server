package com.adplatform.restApi.wallet.dto;

import com.adplatform.restApi.wallet.domain.WalletLog;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WalletLogMapper {
//    @Mapping(target = "tradeNo", source = "tradeNo")
//    @Mapping(target = "inAmount", source = "request.inAmount", ignore=true)
//    @Mapping(target = "outAmount", source = "request.outAmount", ignore=true)
//    @Mapping(target = "balance", source = "balance")
//    @Mapping(target = "summary", source = "request.summary")
//    @Mapping(target = "memo", source = "request.memo")
//    @Mapping(target = "createdUserNo", source = "loginUserNo")
    WalletLog toEntity(WalletDto.Request.SaveCash request, Integer loginUserNo);
}
