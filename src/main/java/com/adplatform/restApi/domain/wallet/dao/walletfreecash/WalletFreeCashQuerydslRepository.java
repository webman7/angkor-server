package com.adplatform.restApi.domain.wallet.dao.walletfreecash;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletFreeCashQuerydslRepository {

    void updateFreeCashStats(Integer id, String status, Integer updateUserId);

    Page<WalletDto.Response.FreeCashSearch> searchForFreeCash(Pageable pageable, WalletDto.Request.FreeCashSearch request);
}
