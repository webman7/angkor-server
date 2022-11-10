package com.adplatform.restApi.domain.wallet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_master")
public class WalletMaster {
    @Id
    @Column(name = "adaccount_info_id", columnDefinition = "Integer")
    private Integer adAccountInfoId;

    @Column(name = "open_date", columnDefinition = "Integer")
    private Integer openDate;

    @Column(name = "close_date", columnDefinition = "Integer")
    private Integer closeDate;
}
