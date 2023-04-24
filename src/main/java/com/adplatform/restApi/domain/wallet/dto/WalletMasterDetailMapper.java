package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletMasterDetail;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WalletMasterDetailMapper {
    WalletMasterDetail toEntity(WalletDto.Request.SaveWalletMasterDetail request, Integer loginUserNo);
}
