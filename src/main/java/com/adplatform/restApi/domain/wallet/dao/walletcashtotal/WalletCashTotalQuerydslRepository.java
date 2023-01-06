package com.adplatform.restApi.domain.wallet.dao.walletcashtotal;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;

import java.util.List;

public interface WalletCashTotalQuerydslRepository {

    void updateWalletCashAdd(Integer adAccountId, Integer cashId, Long amount, Long availableAmount);

    void saveWalletCashReserve(Integer adAccountId, Integer cashId, Long availableAmount, Long reserveAmount);

    List<WalletDto.Response.WalletCashTotal> getWalletCashTotal(Integer adAccountId);
}
