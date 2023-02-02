package com.adplatform.restApi.advertiser.wallet.dao.walletfreecash;

import com.adplatform.restApi.advertiser.wallet.dto.WalletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletFreeCashQuerydslRepository {

    void updateFreeCashStats(Integer id, String status, Integer updateUserId);

    Page<WalletDto.Response.FreeCashSearch> searchForFreeCash(Pageable pageable, WalletDto.Request.FreeCashSearch request);
}