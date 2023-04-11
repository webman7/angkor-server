package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class AdminUserAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "adminUserAlreadyExistException.code";
    private static final String MESSAGE_KEY = "adminUserAlreadyExistException.message";

    public AdminUserAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
