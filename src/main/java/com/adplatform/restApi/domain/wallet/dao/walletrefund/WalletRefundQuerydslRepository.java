package com.adplatform.restApi.domain.wallet.dao.walletrefund;

import com.adplatform.restApi.domain.wallet.domain.WalletRefund;
import com.adplatform.restApi.domain.wallet.domain.WalletRefundFile;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletRefundQuerydslRepository {
    Page<WalletDto.Response.RefundSearch> searchForRefund(Pageable pageable, WalletDto.Request.RefundSearch request);

//    WalletRefundFile findDetailFilesById(Integer id);
}
