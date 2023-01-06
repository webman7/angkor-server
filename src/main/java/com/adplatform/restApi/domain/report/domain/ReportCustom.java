package com.adplatform.restApi.domain.report.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.converter.StringListToStringConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import com.adplatform.restApi.global.value.Email;
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

    @Column(name = "report_level")
    private String reportLevel;

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

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean deleted;

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
            String reportLevel,
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

    public ReportCustom update(ReportCustomDto.Request.Update request) {
        this.name = request.getName();
        this.reportLevel = request.getReportLevel();
        this.configs = request.getConfigs();
        this.indicators = request.getIndicators();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

}
