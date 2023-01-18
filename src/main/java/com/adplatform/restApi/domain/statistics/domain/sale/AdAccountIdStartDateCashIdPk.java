package com.adplatform.restApi.domain.statistics.domain.sale;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AdAccountIdStartDateCashIdPk implements Serializable {
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "stat_date")
    private Integer statDate;

    @Column(name = "cash_info_id")
    private Integer cashId;
}