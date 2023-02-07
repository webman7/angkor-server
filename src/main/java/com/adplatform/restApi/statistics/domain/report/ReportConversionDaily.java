package com.adplatform.restApi.statistics.domain.report;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ReportDailyId.class)
@Entity
@Table(name = "report_conversion_daily")
public class ReportConversionDaily {
    @Id
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Id
    @Column(name = "campaign_info_id")
    private Integer campaignId;

    @Id
    @Column(name = "adgroup_info_id")
    private Integer adGroupId;

    @Id
    @Column(name = "report_date")
    private Integer reportDate;

    @Id
    @Column(name = "creative_info_id")
    private Integer creativeId;

    @Embedded
    private ReportConversionInformation information;
}
