package com.adplatform.restApi.domain.advertiser.creative.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class CreativeReviewAlreadyApproveException extends BaseException {
    private static final String CODE_KEY = "creativeReviewAlreadyApproveException.code";
    private static final String MESSAGE_KEY = "creativeReviewAlreadyApproveException.message";

    public CreativeReviewAlreadyApproveException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
