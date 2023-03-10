package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportDailyId extends ReportAdGroupDailyId implements Serializable {
    private Integer businessAccountId;
    private Integer adAccountId;
    private Integer campaignId;
    private Integer adGroupId;
    private Integer mediaId;
    private Integer placementId;
    private Integer creativeId;
    private Integer reportDate;
}
