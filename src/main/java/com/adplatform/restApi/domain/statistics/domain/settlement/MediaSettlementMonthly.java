package com.adplatform.restApi.domain.statistics.domain.settlement;

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
@Table(name = "media_settlement_monthly")
public class MediaSettlementMonthly extends BaseEntity {
    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "media_info_id")
    private int mediaId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount")
    private int supplyAmount;

    @Column(name = "commission_rate")
    private int commissionRate;

    @Column(name = "commission_supply_amount")
    private int commissionSupplyAmount;

    @Column(name = "commission_vat_amount")
    private int commissionVatAmount;

    @Column(name = "commission_amount")
    private int commissionAmount;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "issue_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean issueStatus;

    @Builder
    public MediaSettlementMonthly(
            Integer adAccountId,
            Integer mediaId,
            Integer statDate,
            Integer supplyAmount,
            Integer commissionRate,
            Integer commissionSupplyAmount,
            Integer commissionVatAmount,
            Integer commissionAmount,
            boolean issueStatus) {
        this.adAccountId = adAccountId;
        this.mediaId = mediaId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.commissionRate = commissionRate;
        this.commissionSupplyAmount = commissionSupplyAmount;
        this.commissionVatAmount = commissionVatAmount;
        this.commissionAmount = commissionAmount;
        this.issueStatus = issueStatus;
    }
}
