package com.adplatform.restApi.domain.statistics.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class MediaTaxBillUpdateException extends BaseException {
    private static final String CODE_KEY = "mediaTaxBillUpdateException.code";
    private static final String MESSAGE_KEY = "mediaTaxBillUpdateException.message";

    public MediaTaxBillUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}

