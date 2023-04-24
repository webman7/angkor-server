package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserve;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WalletCampaignReserveMapper {
    WalletCampaignReserve toEntity(WalletDto.Request.SaveWalletCampaignReserve request, Integer loginUserNo);
}
