package com.adplatform.restApi.domain.statistics.domain.settlement;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_settlement_daily")
public class AdAccountSettlementDaily {

    @EmbeddedId
    private final AdAccountIdStatDateSettlementPk id = new AdAccountIdStatDateSettlementPk();

    @Column(name = "owner_company_info_id")
    private int ownerCompanyId;

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "supply_amount")
    private int supplyAmount;

    @Column(name = "vat_amount")
    private int vatAmount;

    @Column(name = "total_amount")
    private int totalAmount;

    @Builder
    public AdAccountSettlementDaily(
            Integer adAccountId,
            Integer statDate,
            Integer ownerCompanyId,
            Integer companyId,
            Integer supplyAmount,
            Integer vatAmount,
            Integer totalAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.ownerCompanyId = ownerCompanyId;
        this.companyId = companyId;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
    }
}
