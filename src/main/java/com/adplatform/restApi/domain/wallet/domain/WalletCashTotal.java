package com.adplatform.restApi.domain.wallet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_cash_total")
@IdClass(WalletCashTotalId.class)
public class WalletCashTotal implements Serializable {
    @Id
    @Column(name = "adaccount_info_id", columnDefinition = "Integer")
    private Integer adAccountInfoId;

    @Id
    @Column(name = "cash_info_id", columnDefinition = "Integer")
    private Integer cashInfoId;

    @Column(name = "amount", columnDefinition = "Integer")
    private Integer amount;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;
}
