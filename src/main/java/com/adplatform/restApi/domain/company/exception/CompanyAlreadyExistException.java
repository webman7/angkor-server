package com.adplatform.restApi.domain.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CompanyAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "companyAlreadyExistException.code";
    private static final String MESSAGE_KEY = "companyAlreadyExistException.message";

    public CompanyAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
