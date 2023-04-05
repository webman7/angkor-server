package com.adplatform.restApi.domain.statistics.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaTaxBillAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "mediaTaxBillAlreadyExistException.code";
    private static final String MESSAGE_KEY = "mediaTaxBillAlreadyExistException.message";

    public MediaTaxBillAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}

