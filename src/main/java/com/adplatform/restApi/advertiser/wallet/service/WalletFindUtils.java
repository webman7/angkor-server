package com.adplatform.restApi.advertiser.wallet.service;

import com.adplatform.restApi.advertiser.adaccount.exception.AdAccountNotFoundException;
import com.adplatform.restApi.advertiser.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.advertiser.wallet.domain.WalletMaster;

public class WalletFindUtils {
    public static WalletMaster findByIdOrElseThrow(Integer id, WalletMasterRepository repository) {
        return repository.findById(id)
                .orElseThrow(AdAccountNotFoundException::new);
    }
}
