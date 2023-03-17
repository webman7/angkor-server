package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_charge_log")
public class WalletChargeLog extends BaseCreatedEntity {

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "deposit_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float depositAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "deposit_date")
    private LocalDateTime depositAt;

    @Column(name = "depositor", length = 30)
    private String depositor;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;
}
