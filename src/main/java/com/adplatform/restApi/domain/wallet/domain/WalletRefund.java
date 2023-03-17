package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_refund")
public class WalletRefund extends BaseUpdatedEntity {

    public enum SendYN {
        /** 환불요청 */
        N,
        /** 환불완료 */
        Y
    }

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "bank_info_id")
    private Integer bankId;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "account_owner", length = 50)
    private String accountOwner;

    @Column(name = "request_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float requestAmount;

    @Column(name = "amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float amount;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_yn", columnDefinition = "CHAR")
    private SendYN sendYN;

}
