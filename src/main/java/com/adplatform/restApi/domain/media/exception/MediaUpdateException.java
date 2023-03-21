package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaUpdateException extends BaseException {
    private static final String CODE_KEY = "mediaUpdateException.code";
    private static final String MESSAGE_KEY = "mediaUpdateException.message";

    public MediaUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}