package com.adplatform.restApi.wallet.service;

import com.adplatform.restApi.adaccount.exception.AdAccountNotFoundException;
import com.adplatform.restApi.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.wallet.domain.WalletMaster;

public class WalletFindUtils {
    public static WalletMaster findByIdOrElseThrow(Integer id, WalletMasterRepository repository) {
        return repository.findById(id)
                .orElseThrow(AdAccountNotFoundException::new);
    }
}
