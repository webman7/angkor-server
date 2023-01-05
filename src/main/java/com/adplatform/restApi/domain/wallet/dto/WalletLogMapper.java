package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.domain.WalletLog;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface WalletLogMapper {
//    @Mapping(target = "tradeNo", source = "tradeNo")
//    @Mapping(target = "inAmount", source = "request.inAmount", ignore=true)
//    @Mapping(target = "outAmount", source = "request.outAmount", ignore=true)
//    @Mapping(target = "balance", source = "balance")
//    @Mapping(target = "summary", source = "request.summary")
//    @Mapping(target = "memo", source = "request.memo")
    @Mapping(target = "createdUserId", source = "loginUserId")
    WalletLog toEntity(WalletDto.Request.Save request, Integer loginUserId);
}
