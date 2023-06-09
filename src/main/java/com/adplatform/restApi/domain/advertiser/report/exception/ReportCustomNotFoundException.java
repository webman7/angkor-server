package com.adplatform.restApi.domain.advertiser.report.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class ReportCustomNotFoundException extends BaseException {
    private static final String CODE_KEY = "reportCustomNotFoundException.code";
    private static final String MESSAGE_KEY = "reportCustomNotFoundException.message";

    public ReportCustomNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}

