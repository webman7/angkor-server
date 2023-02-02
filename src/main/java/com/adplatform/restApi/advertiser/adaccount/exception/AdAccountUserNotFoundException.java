package com.adplatform.restApi.advertiser.adaccount.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountUserNotFoundException extends BaseException {
    private static final String CODE_KEY = "adAccountUserNotFoundException.code";
    private static final String MESSAGE_KEY = "adAccountUserNotFoundException.message";

    public AdAccountUserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
