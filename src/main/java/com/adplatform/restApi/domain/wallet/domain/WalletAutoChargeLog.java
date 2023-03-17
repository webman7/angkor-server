package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_auto_charge_log")
public class WalletAutoChargeLog extends BaseCreatedEntity {

    public enum Status {
        /** 최초상태 */
        I,
        /** 에러 */
        E,
        /** 완료 */
        F
    }

    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "request_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float requestAmount;

    @Column(name = "amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;

    @Column(name = "approve_no")
    private String approveNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "approve_date")
    private LocalDateTime approveAt;

}
