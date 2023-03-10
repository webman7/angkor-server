package com.adplatform.restApi.domain.wallet.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class WalletMasterNotFoundException extends BaseException {
    private static final String CODE_KEY = "walletMasterNotFoundException.code";
    private static final String MESSAGE_KEY = "walletMasterNotFoundException.message";

    public WalletMasterNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
