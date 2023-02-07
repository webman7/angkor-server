package com.adplatform.restApi.domain.adaccount.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountNotFoundException extends BaseException {
    private static final String CODE_KEY = "adAccountNotFoundException.code";
    private static final String MESSAGE_KEY = "adAccountNotFoundException.message";

    public AdAccountNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
