package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BusinessAccountUserAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "businessAccountUserAlreadyExistException.code";
    private static final String MESSAGE_KEY = "businessAccountUserAlreadyExistException.message";

    public BusinessAccountUserAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}