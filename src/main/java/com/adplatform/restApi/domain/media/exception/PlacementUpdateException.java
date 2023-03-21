package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class PlacementUpdateException extends BaseException {
    private static final String CODE_KEY = "placementUpdateException.code";
    private static final String MESSAGE_KEY = "placementUpdateException.message";

    public PlacementUpdateException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}