package com.adplatform.restApi.domain.adgroup.exception;

import com.adplatform.restApi.global.error.exception.BaseException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class DeviceNotFoundException extends BaseException {
    private static final String CODE_KEY = "deviceNotFoundException.code";
    private static final String MESSAGE_KEY = "deviceNotFoundException.message";

    public DeviceNotFoundException() {
        super(CODE_KEY, MESSAGE_KEY);
    }
}
