package com.adplatform.restApi.domain.adaccount.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class AdAccountUserNotFoundException extends BaseException {
    private static final String CODE_KEY = "adAccountUserNotFoundException.code";
    private static final String MESSAGE_KEY = "adAccountUserNotFoundException.message";

    public AdAccountUserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
