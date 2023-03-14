package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaCompanyUserMasterException extends BaseException {
    private static final String CODE_KEY = "mediaCompanyUserMasterException.code";
    private static final String MESSAGE_KEY = "mediaCompanyUserMasterException.message";

    public MediaCompanyUserMasterException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
