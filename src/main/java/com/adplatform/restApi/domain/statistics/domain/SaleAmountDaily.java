package com.adplatform.restApi.domain.statistics.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale_amount_daily")
public class SaleAmountDaily {
    @EmbeddedId
    private AdAccountIdStartDatePk id;

    @Column(name = "sale_amount")
    private int saleAmount;

    @Column(name = "sale_cnt")
    private int saleCount;
}
