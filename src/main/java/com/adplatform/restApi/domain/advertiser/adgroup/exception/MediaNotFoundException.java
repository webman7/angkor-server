package com.adplatform.restApi.domain.advertiser.adgroup.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class MediaNotFoundException extends BaseException {
    private static final String CODE_KEY = "mediaNotFoundException.code";
    private static final String MESSAGE_KEY = "mediaNotFoundException.message";

    public MediaNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
