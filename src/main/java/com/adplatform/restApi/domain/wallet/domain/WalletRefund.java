package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;
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
@Table(name = "wallet_refund")
public class WalletRefund extends BaseCreatedEntity {

    public enum SendYN {
        /** 환불요청 */
        N,
        /** 환불불가 */
        R,
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

    @OneToMany(mappedBy = "walletRefund", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WalletRefundFile> walletRefundFiles = new ArrayList<>();

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Enumerated(EnumType.STRING)
    @Column(name = "send_yn", columnDefinition = "CHAR")
    private SendYN sendYN;

    @Column(name = "upd_user_no")
    private Integer updatedUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;

    @Builder
    public WalletRefund(Integer businessAccountId, Integer bankId, String accountNumber, String accountOwner, Float requestAmount, Float amount, String adminMemo, SendYN sendYN) {
        this.businessAccountId = businessAccountId;
        this.bankId = bankId;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.requestAmount = requestAmount;
        this.amount = amount;
        this.adminMemo = adminMemo;
        this.sendYN = sendYN;
    }

    public void addWalletRefundFile(WalletRefundFile file) {
        this.walletRefundFiles.add(file);
    }

    public WalletRefund updateApprove(WalletDto.Request.UpdateRefund request, Integer loginUserNo) {
        this.amount = request.getAmount();
        this.adminMemo = request.getAdminMemo();
        this.updatedUserNo = loginUserNo;
        this.updatedAt = LocalDateTime.now();
        this.sendYN = SendYN.Y;
        return this;
    }

    public WalletRefund updateReject(WalletDto.Request.UpdateRefund request, Integer loginUserNo) {
        this.adminMemo = request.getAdminMemo();
        this.updatedUserNo = loginUserNo;
        this.updatedAt = LocalDateTime.now();
        this.sendYN = SendYN.R;
        return this;
    }
}
