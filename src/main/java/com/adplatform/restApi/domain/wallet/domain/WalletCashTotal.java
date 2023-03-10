package com.adplatform.restApi.domain.wallet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author junny
 * @since 1.0
 */
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(AuditingEntityListener.class)
//@Entity
//@Table(name = "wallet_cash_total")
//@DynamicInsert
public class WalletCashTotal {
//    @EmbeddedId
//    private WalletCashTotalId id = new WalletCashTotalId();
//
//    @MapsId("walletMasterId")
//    @ManyToOne
//    @JoinColumn(name = "adaccount_info_id")
//    private WalletMaster walletMaster;
//
//    @MapsId("cashId")
//    @ManyToOne
//    @JoinColumn(name = "cash_info_id")
//    private Cash cash;
//
//    @Column(name = "amount", columnDefinition = "INT")
//    private Long amount;
//
//    @Column(name = "available_amount", columnDefinition = "INT")
//    private Long availableAmount;
//
//    @Column(name = "reserve_amount", columnDefinition = "INT")
//    private Long reserveAmount;
//
//    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    @Column(name = "upd_date")
//    private LocalDateTime updatedAt;
//
//    public WalletCashTotal(WalletMaster walletMaster, Cash cash) {
//        this.walletMaster = walletMaster;
//        this.cash = cash;
//    }
}
