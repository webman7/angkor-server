package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_charge_files")
public class WalletChargeFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_charge_log_id")
    private WalletChargeLog walletChargeLog;

    @Embedded
    private FileInformation information;

    public WalletChargeFile(WalletChargeLog walletChargeLog, FileInformation information) {
        this.walletChargeLog = walletChargeLog;
        this.information = information;
    }

    public WalletChargeFile copy(WalletChargeLog walletChargeLog) {
        WalletChargeFile copy = new WalletChargeFile();
        copy.walletChargeLog = walletChargeLog;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}