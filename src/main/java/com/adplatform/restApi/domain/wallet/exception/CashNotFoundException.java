package com.adplatform.restApi.domain.wallet.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CashNotFoundException extends BaseException {
    private static final String CODE_KEY = "cashNotFoundException.code";
    private static final String MESSAGE_KEY = "cashNotFoundException.message";

    public CashNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
