package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaCompanyUserNotFoundException extends BaseException {
    private static final String CODE_KEY = "mediaCompanyUserNotFoundException.code";
    private static final String MESSAGE_KEY = "mediaCompanyUserNotFoundException.message";

    public MediaCompanyUserNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
