package com.adplatform.restApi.advertiser.creative.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CreativeNotFoundException extends BaseException {
    private static final String CODE_KEY = "creativeNotFoundException.code";
    private static final String MESSAGE_KEY = "creativeNotFoundException.message";

    public CreativeNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
