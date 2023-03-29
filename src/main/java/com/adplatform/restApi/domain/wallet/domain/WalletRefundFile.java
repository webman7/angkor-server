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
@Table(name = "wallet_refund_files")
public class WalletRefundFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_refund_id")
    private WalletRefund walletRefund;

    @Embedded
    private FileInformation information;

    public WalletRefundFile(WalletRefund walletRefund, FileInformation information) {
        this.walletRefund = walletRefund;
        this.information = information;
    }

    public WalletRefundFile copy(WalletRefund walletRefund) {
        WalletRefundFile copy = new WalletRefundFile();
        copy.walletRefund = walletRefund;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}