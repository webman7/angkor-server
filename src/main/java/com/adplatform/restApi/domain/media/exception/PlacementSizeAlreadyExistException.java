package com.adplatform.restApi.domain.media.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

public class PlacementSizeAlreadyExistException extends BaseException {
    private static final String CODE_KEY = "placementSizeAlreadyExistException.code";
    private static final String MESSAGE_KEY = "placementSizeAlreadyExistException.message";

    public PlacementSizeAlreadyExistException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
