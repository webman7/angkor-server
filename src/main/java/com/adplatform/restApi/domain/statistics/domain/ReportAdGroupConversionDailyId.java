package com.adplatform.restApi.domain.statistics.domain;

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
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportAdGroupConversionDailyId implements Serializable {
    private Integer adAccountId;
    private Integer campaignId;
    private Integer adGroupId;
    private Integer reportDate;
}
