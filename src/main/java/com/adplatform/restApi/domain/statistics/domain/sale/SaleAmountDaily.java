package com.adplatform.restApi.domain.statistics.domain.sale;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUserId;
import com.adplatform.restApi.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private final AdAccountIdStartDatePk id = new AdAccountIdStartDatePk();

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "owner_company_info_id")
    private int ownerCompanyId;

    @Column(name = "sale_amount")
    private int saleAmount;

    @Builder
    public SaleAmountDaily(
            Integer adAccountId,
            Integer statDate,
            Integer companyId,
            Integer ownerCompanyId,
            Integer saleAmount) {
        this.id.setAdAccountId(adAccountId);
        this.id.setStatDate(statDate);
        this.companyId = companyId;
        this.ownerCompanyId = ownerCompanyId;
        this.saleAmount = saleAmount;
    }
}
