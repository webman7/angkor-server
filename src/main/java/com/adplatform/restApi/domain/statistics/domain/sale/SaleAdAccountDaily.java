package com.adplatform.restApi.domain.statistics.domain.sale;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale_adaccount_daily")
public class SaleAdAccountDaily {
    @EmbeddedId
    private final SaleAdAccountDailyPk id = new SaleAdAccountDailyPk();

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "pre_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean prePayment;

    @Column(name = "supply_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float supplyAmount;

    @Column(name = "vat_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float vatAmount;

    @Column(name = "total_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalAmount;

    @Builder
    public SaleAdAccountDaily(
            Integer businessAccountId,
            Integer adAccountId,
            Integer statDate,
            Boolean prePayment,
            Float supplyAmount,
            Float vatAmount,
            Float totalAmount) {
        this.id.setBusinessAccountId(businessAccountId);
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.prePayment = prePayment;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
    }
}
