package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CategoryNotFoundException extends BaseException {
    private static final String CODE_KEY = "categoryNotFoundException.code";
    private static final String MESSAGE_KEY = "categoryNotFoundException.message";

    public CategoryNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}