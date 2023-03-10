package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MediaReportDailyPk implements Serializable {
    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "media_info_id")
    private int mediaId;

    @Column(name = "report_date")
    private Integer reportDate;
}