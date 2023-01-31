package com.adplatform.restApi.domain.wallet.dao.walletlog;

import com.adplatform.restApi.domain.wallet.domain.Cash;
import com.adplatform.restApi.domain.wallet.domain.WalletCashTotal;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletLogQuerydslRepository {
    Page<WalletDto.Response.CashSearch> searchForCash(Pageable pageable, WalletDto.Request.CashSearch request);
}
