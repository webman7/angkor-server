package com.adplatform.restApi.wallet.domain;

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
public class WalletCashTotalId implements Serializable {
    @Column(name = "adaccount_info_id")
    private Integer walletMasterId;

    @Column(name = "cash_info_id")
    private Integer cashId;
}
