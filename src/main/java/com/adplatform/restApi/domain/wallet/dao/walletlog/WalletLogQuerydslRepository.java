package com.adplatform.restApi.domain.wallet.dao.walletlog;

import com.adplatform.restApi.domain.wallet.domain.Cash;
import com.adplatform.restApi.domain.wallet.domain.WalletCashTotal;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;

import java.util.List;

public interface WalletLogQuerydslRepository {
    Integer getNewTradeNo(Integer adAccountId);

//    Long getCashTotalByCashId(Integer adAccountId, Integer cashId);
    WalletDto.Response.WalletCashTotal getCashTotalByCashId(Integer adAccountId, Integer cashId);

}
