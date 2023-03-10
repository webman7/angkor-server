package com.adplatform.restApi.domain.statistics.domain.report;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_report_daily")
public class MediaReportDaily {

    @EmbeddedId
    private final MediaReportDailyPk id = new MediaReportDailyPk();

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "impression")
    private int impression;

    @Column(name = "click")
    private int click;

    @Builder
    public MediaReportDaily(
            Integer businessAccountId,
            Integer adAccountId,
            Integer mediaId,
            Integer reportDate,
            Integer companyId,
            Integer impression,
            Integer click) {
        this.id.setBusinessAccountId(businessAccountId);
        this.id.setAdAccountId(adAccountId);
        this.id.setMediaId(mediaId);
        this.id.setReportDate(reportDate);
        this.companyId = companyId;
        this.impression = impression;
        this.click = click;
    }
}
