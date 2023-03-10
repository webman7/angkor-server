package com.adplatform.restApi.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum Range {
    /** Equal */
    EQ("="),
    /** Greater than or equal to */
    GTE(">="),
    /** Greater then */
    GT(">"),
    /** Less than or equal to */
    LTE("<="),
    /** Less than */
    LT("<");

    private final String value;
}
