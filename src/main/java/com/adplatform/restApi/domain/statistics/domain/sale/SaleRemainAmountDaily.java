package com.adplatform.restApi.domain.statistics.domain.sale;

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
@Table(name = "sale_remain_amount_daily")
public class SaleRemainAmountDaily {

    @EmbeddedId
    private final AdAccountIdStartDatePk id = new AdAccountIdStartDatePk();

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "owner_company_info_id")
    private int ownerCompanyId;

    @Column(name = "remain_amount")
    private int remainAmount;

    @Builder
    public SaleRemainAmountDaily(
            Integer adAccountId,
            Integer statDate,
            Integer companyId,
            Integer ownerCompanyId,
            Integer remainAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.companyId = companyId;
        this.ownerCompanyId = ownerCompanyId;
        this.remainAmount = remainAmount;
    }
}
