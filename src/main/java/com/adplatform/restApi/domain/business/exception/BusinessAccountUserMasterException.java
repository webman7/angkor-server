package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class BusinessAccountUserMasterException extends BaseException {
    private static final String CODE_KEY = "businessAccountUserMasterException.code";
    private static final String MESSAGE_KEY = "businessAccountUserMasterException.message";

    public BusinessAccountUserMasterException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
