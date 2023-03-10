package com.adplatform.restApi.domain.business.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class BusinessAccountNotFoundException extends BaseException {
    private static final String CODE_KEY = "businessAccountNotFoundException.code";
    private static final String MESSAGE_KEY = "businessAccountNotFoundException.message";

    public BusinessAccountNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
