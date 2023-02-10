package com.adplatform.restApi.domain.statistics.domain.settlement;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AdAccountIdStatDateSettlementPk implements Serializable {
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "stat_date")
    private Integer statDate;
}
