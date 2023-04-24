package com.adplatform.restApi.domain.wallet.dao.walletcampaignreserve;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;

public interface WalletCampaignReserveQuerydslRepository {
    WalletDto.Response.WalletCampaignReserve getCampaignReserveAmount(Integer businessAccountId, Integer adAccountId, Integer campaignId);

    void updateCampaignReserveAmount(Integer businessAccountId, Integer adAccountId, Integer campaignId, Float reserveAmount);
}
