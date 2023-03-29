package com.adplatform.restApi.domain.wallet.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class WalletRefundAlreadyRejectException extends BaseException {
    private static final String CODE_KEY = "walletRefundAlreadyRejectException.code";
    private static final String MESSAGE_KEY = "walletRefundAlreadyRejectException.message";

    public WalletRefundAlreadyRejectException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}