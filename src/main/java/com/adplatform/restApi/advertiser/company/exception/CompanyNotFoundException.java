package com.adplatform.restApi.advertiser.company.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CompanyNotFoundException extends BaseException {
    private static final String CODE_KEY = "companyNotFoundException.code";
    private static final String MESSAGE_KEY = "companyNotFoundException.message";

    public CompanyNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
