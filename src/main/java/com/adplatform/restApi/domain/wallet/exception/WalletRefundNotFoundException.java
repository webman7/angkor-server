package com.adplatform.restApi.domain.wallet.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class WalletRefundNotFoundException extends BaseException {
    private static final String CODE_KEY = "walletRefundNotFoundException.code";
    private static final String MESSAGE_KEY = "walletRefundNotFoundException.message";

    public WalletRefundNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
