package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BusinessAccountUserAccountingException extends BaseException {
    private static final String CODE_KEY = "businessAccountUserAccountingException.code";
    private static final String MESSAGE_KEY = "businessAccountUserAccountingException.message";

    public BusinessAccountUserAccountingException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
