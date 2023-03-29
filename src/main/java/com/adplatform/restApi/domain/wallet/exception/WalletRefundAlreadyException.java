package com.adplatform.restApi.domain.wallet.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class WalletRefundAlreadyException extends BaseException {
    private static final String CODE_KEY = "walletRefundAlreadyException.code";
    private static final String MESSAGE_KEY = "walletRefundAlreadyException.message";

    public WalletRefundAlreadyException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}