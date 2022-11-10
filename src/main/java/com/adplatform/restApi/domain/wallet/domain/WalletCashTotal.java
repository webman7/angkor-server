package com.adplatform.restApi.domain.wallet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_cash_total")
public class WalletCashTotal {
    @EmbeddedId
    private WalletCashTotalId id;

    @MapsId("adAccountId")
    @ManyToOne
    @JoinColumn(name = "adaccount_info_id")
    private WalletMaster walletMaster;

    @MapsId("cashId")
    @ManyToOne
    @JoinColumn(name = "cash_info_id")
    private Cash cash;

    @Column(name = "amount", columnDefinition = "Integer")
    private Integer amount;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;
}
