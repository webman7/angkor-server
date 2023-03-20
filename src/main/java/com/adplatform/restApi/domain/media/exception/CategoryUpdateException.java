package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CategoryUpdateException extends BaseException {
    private static final String CODE_KEY = "categoryUpdateException.code";
    private static final String MESSAGE_KEY = "categoryUpdateException.message";

    public CategoryUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}