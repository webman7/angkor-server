package com.adplatform.restApi.domain.wallet.domain;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WalletLogId implements Serializable {
//    @Column(name = "adaccount_info_id")
    private Integer id;

//    @Column(name = "trade_no")
    private Integer tradeNo;
}
