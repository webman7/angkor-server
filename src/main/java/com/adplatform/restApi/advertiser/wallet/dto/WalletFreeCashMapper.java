package com.adplatform.restApi.advertiser.wallet.dto;

import com.adplatform.restApi.advertiser.wallet.domain.WalletFreeCash;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface WalletFreeCashMapper {
    @Mapping(target = "status", expression = "java(WalletFreeCash.Status.READY)")
    WalletFreeCash toEntity(WalletDto.Request.SaveFreeCash request, Integer loginUserNo);
}