package com.adplatform.restApi.domain.advertiser.adgroup.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author junny
 * @since 1.0
 */
public class AdGroupNotFoundException extends BaseException {
    private static final String CODE_KEY = "adGroupNotFoundException.code";
    private static final String MESSAGE_KEY = "adGroupNotFoundException.message";

    public AdGroupNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
