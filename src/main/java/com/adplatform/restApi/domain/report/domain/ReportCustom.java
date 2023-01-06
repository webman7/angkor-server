package com.adplatform.restApi.domain.report.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.StringListToStringConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "report_custom_info")
public class ReportCustom extends BaseUpdatedEntity {
//
//    /**
//     * 지표
//     */
//    public enum ReportLevel {
//        /** 광고계정 */
//        AD_ACCOUNT,
//        /** 캠페인 */
//        CAMPAIGN,
//        /** 광고그룹 */
//        AD_GROUP,
//        /** 소재 */
//        CREATIVE
//    }
//
//    /**
//     * 지표
//     */
//    public enum Indicators {
//        /** 비용지표 */
//        costIndicator,
//        /** 기본지표 */
//        defaultIndicator
//    }
//
//    public enum Config {
//        ON, OFF, DEL
//    }

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "name")
    private String name;

    @Convert(converter = StringListToStringConverter.class)
    @Column(name = "report_level")
    private List<String> reportLevel;

    @Convert(converter = StringListToStringConverter.class)
    @Column(name = "user_config")
    private List<String> configs;

    @Convert(converter = StringListToStringConverter.class)
    @Column(name = "indicators")
    private List<String> indicators;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date")
    private Integer endDate;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "report_level")
//    private ReportLevel reportLevel;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "user_config")
//    private Config config;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "indicators")
//    private Indicators indicators;

    @Builder
    public ReportCustom(
            Integer adAccountId,
            String name,
            List<String> reportLevel,
            List<String> configs,
            List<String> indicators,
            Integer startDate,
            Integer endDate) {
        this.adAccountId = adAccountId;
        this.name = name;
        this.reportLevel = reportLevel;
        this.configs = configs;
        this.indicators = indicators;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
