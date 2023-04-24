package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletCampaignReserveDetail;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface WalletCampaignReserveDetailMapper {

    WalletCampaignReserveDetail toEntity(WalletDto.Request.SaveWalletCampaignReserveDetail request, Integer loginUserNo);
}
