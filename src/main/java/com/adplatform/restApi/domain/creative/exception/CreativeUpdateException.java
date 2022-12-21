package com.adplatform.restApi.domain.creative.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CreativeUpdateException  extends BaseException {
    private static final String CODE_KEY = "creativeUpdateException.code";
    private static final String MESSAGE_KEY = "creativeUpdateException.message";

    public CreativeUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}