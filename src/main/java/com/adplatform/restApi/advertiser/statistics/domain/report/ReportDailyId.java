package com.adplatform.restApi.advertiser.statistics.domain.report;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportDailyId extends ReportAdGroupDailyId implements Serializable {
    private Integer creativeId;
}
