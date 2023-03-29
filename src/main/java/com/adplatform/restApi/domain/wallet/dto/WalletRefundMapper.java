package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletRefund;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface WalletRefundMapper {

    @Mapping(target = "amount", expression = "java(0.0F)")
    @Mapping(target = "adminMemo", ignore = true)
    @Mapping(target = "sendYN", expression = "java(WalletRefund.SendYN.N)")
    WalletRefund toEntity(WalletDto.Request.SaveRefund request, Integer loginUserNo);
}
