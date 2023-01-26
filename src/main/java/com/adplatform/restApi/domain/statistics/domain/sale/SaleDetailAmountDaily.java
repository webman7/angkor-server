package com.adplatform.restApi.domain.statistics.domain.sale;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale_detail_amount_daily")
public class SaleDetailAmountDaily {
    @EmbeddedId
    private final AdAccountIdStartDateCashIdPk id = new AdAccountIdStartDateCashIdPk();

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "owner_company_info_id")
    private int ownerCompanyId;

    @Column(name = "sale_amount")
    private int saleAmount;

    @Builder
    public SaleDetailAmountDaily(
            Integer adAccountId,
            Integer statDate,
            Integer cashId,
            Integer companyId,
            Integer ownerCompanyId,
            Integer saleAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.id.setCashId(cashId);
        this.companyId = companyId;
        this.ownerCompanyId = ownerCompanyId;
        this.saleAmount = saleAmount;
    }
}
