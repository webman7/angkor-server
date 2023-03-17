package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class PlacementNotFoundException extends BaseException {
    private static final String CODE_KEY = "placementNotFoundException.code";
    private static final String MESSAGE_KEY = "placementNotFoundException.message";

    public PlacementNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
