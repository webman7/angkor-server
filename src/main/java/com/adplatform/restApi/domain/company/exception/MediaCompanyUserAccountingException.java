package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaCompanyUserAccountingException extends BaseException {
    private static final String CODE_KEY = "mediaCompanyUserAccountingException.code";
    private static final String MESSAGE_KEY = "mediaCompanyUserAccountingException.message";

    public MediaCompanyUserAccountingException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
