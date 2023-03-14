package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaCompanyUserAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "mediaCompanyUserAlreadyExistException.code";
    private static final String MESSAGE_KEY = "mediaCompanyUserAlreadyExistException.message";

    public MediaCompanyUserAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
