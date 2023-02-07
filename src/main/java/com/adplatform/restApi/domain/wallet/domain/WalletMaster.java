package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "id", column = @Column(name = "adaccount_info_id"))
@Table(name = "wallet_master")
public class WalletMaster extends BaseEntity {
    @MapsId
    @OneToOne
    @JoinColumn(name = "adaccount_info_id")
    private AdAccount adAccount;

    @OneToMany(mappedBy = "walletMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletCashTotal> cashTotals = new ArrayList<>();

    @Column(name = "open_date", columnDefinition = "INT")
    private Integer openDate;

    @Column(name = "close_date", columnDefinition = "INT")
    private Integer closeDate;

    public WalletMaster(AdAccount adAccount, Integer openDate) {
        this.adAccount = adAccount;
        this.openDate = openDate;
    }

    public static WalletMaster create() {
        return new WalletMaster(null, Integer.parseInt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
    }

    public WalletMaster updateAdAccount(AdAccount adAccount) {
        this.adAccount = adAccount;
        return this;
    }

    public WalletMaster initWalletCashTotal (List<Cash> cashes) {
        cashes.forEach(cash -> this.cashTotals.add(new WalletCashTotal(this, cash)));
        return this;
    }
}
