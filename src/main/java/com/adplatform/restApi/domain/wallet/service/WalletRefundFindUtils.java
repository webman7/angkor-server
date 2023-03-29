package com.adplatform.restApi.domain.wallet.service;

import com.adplatform.restApi.domain.wallet.dao.walletrefund.WalletRefundRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletRefund;
import com.adplatform.restApi.domain.wallet.exception.WalletMasterNotFoundException;
import com.adplatform.restApi.domain.wallet.exception.WalletRefundNotFoundException;

public class WalletRefundFindUtils {
    public static WalletRefund findByIdOrElseThrow(Integer id, WalletRefundRepository repository) {
        return repository.findById(id)
                .orElseThrow(WalletRefundNotFoundException::new);
    }
}
