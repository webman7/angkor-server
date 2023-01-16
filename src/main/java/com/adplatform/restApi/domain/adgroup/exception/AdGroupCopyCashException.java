package com.adplatform.restApi.domain.adgroup.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class AdGroupCopyCashException extends BaseException {
    private static final String CODE_KEY = "adGroupCopyCashException.code";
    private static final String MESSAGE_KEY = "adGroupCopyCashException.message";

    public AdGroupCopyCashException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
