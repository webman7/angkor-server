package com.adplatform.restApi.advertiser.adgroup.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdGroupNotFoundException extends BaseException {
    private static final String CODE_KEY = "adGroupNotFoundException.code";
    private static final String MESSAGE_KEY = "adGroupNotFoundException.message";

    public AdGroupNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
