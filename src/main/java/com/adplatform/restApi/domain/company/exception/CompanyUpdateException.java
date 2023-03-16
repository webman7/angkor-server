package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CompanyUpdateException extends BaseException {
    private static final String CODE_KEY = "companyUpdateException.code";
    private static final String MESSAGE_KEY = "companyUpdateException.message";

    public CompanyUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
