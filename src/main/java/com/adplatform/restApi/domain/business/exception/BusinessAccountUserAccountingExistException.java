package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BusinessAccountUserAccountingExistException extends BaseException {
    private static final String CODE_KEY = "businessAccountUserAccountingExistException.code";
    private static final String MESSAGE_KEY = "businessAccountUserAccountingExistException.message";

    public BusinessAccountUserAccountingExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
