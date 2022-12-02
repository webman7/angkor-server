package com.adplatform.restApi.domain.statistics.domain.report;

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
@IdClass(ReportAdGroupDailyId.class)
@Entity
@Table(name = "report_adgroup_conversion_daily")
public class ReportAdGroupConversionDaily {
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

    @Embedded
    private ReportConversionInformation information;
}
