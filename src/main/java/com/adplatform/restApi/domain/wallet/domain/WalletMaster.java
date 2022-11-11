package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
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
        return new WalletMaster(null, Integer.getInteger(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
    }

    public WalletMaster updateAdAccount(AdAccount adAccount) {
        this.adAccount = adAccount;
        return this;
    }
}
