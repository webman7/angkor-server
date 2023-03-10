package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class BusinessAccountUserNotFoundException extends BaseException {
    private static final String CODE_KEY = "businessAccountUserNotFoundException.code";
    private static final String MESSAGE_KEY = "businessAccountUserNotFoundException.message";

    public BusinessAccountUserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
