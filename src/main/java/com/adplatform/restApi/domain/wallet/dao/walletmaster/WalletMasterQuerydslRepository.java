package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;

import java.util.List;

public interface WalletMasterQuerydslRepository {
    WalletDto.Response.WalletMaster getWalletMaster(Integer businessAccountId);
    void updateWalletMaster(Integer businessAccountId, Float availableAmount, Float totalReserveAmount);

    void updateWalletMasterReserveAmount(Integer businessAccountId, Float totalReserveAmount);

    void updateWalletMasterCharge(Integer businessAccountId, Float availableAmount);
}
