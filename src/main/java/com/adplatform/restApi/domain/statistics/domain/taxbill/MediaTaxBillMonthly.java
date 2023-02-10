package com.adplatform.restApi.domain.statistics.domain.taxbill;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_tax_bill_monthly")
public class MediaTaxBillMonthly extends BaseEntity {
    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "media_info_id")
    private int mediaId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount")
    private int supplyAmount;

    @Column(name = "vat_amount")
    private int vatAmount;

    @Column(name = "total_amount")
    private int totalAmount;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "issue_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean issueStatus;

    @Builder
    public MediaTaxBillMonthly(
            Integer adAccountId,
            Integer mediaId,
            Integer statDate,
            Integer supplyAmount,
            Integer vatAmount,
            Integer totalAmount,
            boolean issueStatus) {
        this.adAccountId = adAccountId;
        this.mediaId = mediaId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.issueStatus = issueStatus;
    }
}
