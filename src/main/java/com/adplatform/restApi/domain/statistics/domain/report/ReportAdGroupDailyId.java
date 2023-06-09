package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportAdGroupDailyId implements Serializable {
    private Integer businessAccountId;
    private Integer adAccountId;
    private Integer campaignId;
    private Integer adGroupId;
    private Integer mediaId;
    private Integer placementId;
    private Integer reportDate;
}
