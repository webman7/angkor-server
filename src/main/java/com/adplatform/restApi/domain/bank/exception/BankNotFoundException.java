package com.adplatform.restApi.domain.bank.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BankNotFoundException extends BaseException {
    private static final String CODE_KEY = "bankNotFoundException.code";
    private static final String MESSAGE_KEY = "bankNotFoundException.message";

    public BankNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
