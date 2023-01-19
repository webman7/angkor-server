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

    @Column(name = "sale_amount")
    private int saleAmount;

    @Builder
    public SaleDetailAmountDaily(
            Integer adAccountId,
            Integer statDate,
            Integer cashId,
            Integer saleAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.id.setCashId(cashId);
        this.saleAmount = saleAmount;
    }
}
