package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "business_account_info_id"))
@Table(name = "wallet_master")
public class WalletMaster extends BaseEntity {
    @MapsId
    @OneToOne
    @JoinColumn(name = "business_account_info_id")
    private BusinessAccount businessAccount;

    @Column(name = "available_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float availableAmount;

    @Column(name = "total_reserve_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalReserveAmount;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Column(name = "open_date", columnDefinition = "INT")
    private Integer openDate;

    @Column(name = "close_date", columnDefinition = "INT")
    private Integer closeDate;

    public WalletMaster(BusinessAccount businessAccount, Integer openDate, Float availableAmount, Float totalReserveAmount) {
        this.businessAccount = businessAccount;
        this.openDate = openDate;
        this.availableAmount = availableAmount;
        this.totalReserveAmount = totalReserveAmount;
    }

    public static WalletMaster create() {
        return new WalletMaster(null, Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))), 0.0F, 0.0F);
    }

    public WalletMaster updateBusinessAccount(BusinessAccount businessAccount) {
        this.businessAccount = businessAccount;
        return this;
    }

//    public WalletMaster initWalletCashTotal (List<Cash> cashes) {
//        cashes.forEach(cash -> this.cashTotals.add(new WalletCashTotal(this, cash)));
//        return this;
//    }
}
