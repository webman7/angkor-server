package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaNotFoundException extends BaseException {
    private static final String CODE_KEY = "mediaNotFoundException.code";
    private static final String MESSAGE_KEY = "mediaNotFoundException.message";

    public MediaNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}