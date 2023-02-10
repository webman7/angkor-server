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
@Table(name = "media_settlement_daily")
public class MediaSettlementDaily {
    @EmbeddedId
    private final AdAccountIdStatDateMediaIdPk id = new AdAccountIdStatDateMediaIdPk();

    @Column(name = "supply_amount")
    private int supplyAmount;

    @Column(name = "vat_amount")
    private int vatAmount;

    @Column(name = "total_amount")
    private int totalAmount;

    @Builder
    public MediaSettlementDaily(
            Integer adAccountId,
            Integer statDate,
            Integer mediaId,
            Integer supplyAmount,
            Integer vatAmount,
            Integer totalAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.id.setMediaId(mediaId);
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
    }
}
