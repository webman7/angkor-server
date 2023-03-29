package com.adplatform.restApi.domain.wallet.dao.walletchargelog;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletChargeLogQuerydslRepository {

    Page<WalletDto.Response.ChargeSearch> searchForCharge(Pageable pageable, WalletDto.Request.ChargeSearch request);
}
