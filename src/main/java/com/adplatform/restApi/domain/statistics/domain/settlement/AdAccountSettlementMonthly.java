package com.adplatform.restApi.domain.statistics.domain.settlement;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_settlement_monthly")
public class AdAccountSettlementMonthly extends BaseEntity {

    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "owner_company_info_id")
    private int ownerCompanyId;

    @Column(name = "company_info_id")
    private int companyId;

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

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "payment_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean paymentStatus;

    @Column(name = "payment_user_no")
    private int paymentUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "payment_date")
    private LocalDateTime paymentAt;

    @Builder
    public AdAccountSettlementMonthly(
            Integer adAccountId,
            Integer ownerCompanyId,
            Integer companyId,
            Integer statDate,
            Integer supplyAmount,
            Integer commissionRate,
            Integer commissionSupplyAmount,
            Integer commissionVatAmount,
            Integer commissionAmount,
            boolean issueStatus) {
        this.adAccountId = adAccountId;
        this.ownerCompanyId = ownerCompanyId;
        this.companyId = companyId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.commissionRate = commissionRate;
        this.commissionSupplyAmount = commissionSupplyAmount;
        this.commissionVatAmount = commissionVatAmount;
        this.commissionAmount = commissionAmount;
        this.issueStatus = issueStatus;
    }
}
