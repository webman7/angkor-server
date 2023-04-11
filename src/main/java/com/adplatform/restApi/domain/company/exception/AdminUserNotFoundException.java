package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class AdminUserNotFoundException extends BaseException {
    private static final String CODE_KEY = "adminUserNotFoundException.code";
    private static final String MESSAGE_KEY = "adminUserNotFoundException.message";

    public AdminUserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
