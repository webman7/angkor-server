package com.adplatform.restApi.domain.wallet.dao.walletcashtotal;

public interface WalletCashTotalQuerydslRepository {

    void updateWalletCashAdd(Integer adAccountId, Integer cashId, Long amount, Long availableAmount);
}
