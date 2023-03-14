package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaCompanyUserAccountingExistException extends BaseException {
    private static final String CODE_KEY = "mediaCompanyUserAccountingExistException.code";
    private static final String MESSAGE_KEY = "mediaCompanyUserAccountingExistException.message";

    public MediaCompanyUserAccountingExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
