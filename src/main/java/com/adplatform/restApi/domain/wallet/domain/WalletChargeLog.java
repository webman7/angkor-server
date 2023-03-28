package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.company.domain.CompanyFile;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_charge_log")
public class WalletChargeLog extends BaseCreatedEntity {

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "deposit_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float depositAmount;

    @Column(name = "deposit_date")
    private Integer depositAt;

    @Column(name = "depositor", length = 30)
    private String depositor;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @OneToMany(mappedBy = "walletChargeLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WalletChargeFile> walletChargeFiles = new ArrayList<>();

    @Builder
    public WalletChargeLog (
            Integer businessAccountId,
            Float depositAmount,
            String depositor,
            Integer depositAt,
            String adminMemo
    ) {
        this.businessAccountId = businessAccountId;
        this.depositAmount = depositAmount;
        this.depositor = depositor;
        this.depositAt = depositAt;
        this.adminMemo = adminMemo;
    }

    public void addWalletChargeFile(WalletChargeFile file) {
        this.walletChargeFiles.add(file);
    }
}
