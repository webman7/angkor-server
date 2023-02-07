package com.adplatform.restApi.domain.wallet.service;

import com.adplatform.restApi.domain.adaccount.exception.AdAccountNotFoundException;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;

public class WalletFindUtils {
    public static WalletMaster findByIdOrElseThrow(Integer id, WalletMasterRepository repository) {
        return repository.findById(id)
                .orElseThrow(AdAccountNotFoundException::new);
    }
}
