package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.global.entity.PubUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_free_cash")
public class WalletFreeCash  extends PubUpdatedEntity {

    /**
     * 무료 캐시 상태.
     */
    public enum Status {
        /** 사용전 */
        READY,
        /** 사용완료 */
        USED,
        /** 만료 */
        EXPIRED,
        /** 회수 */
        CANCEL
    }

    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "cash_info_id")
    private Integer cashId;

    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "pub_amount", columnDefinition = "INT")
    private Long pubAmount;

    @Column(name = "expire_date", columnDefinition = "INT")
    private Integer expireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Status status;
    @Column(name = "memo", length = 100)
    private String memo;

    @Builder
    public WalletFreeCash(Integer adAccountId, Integer cashId, String summary, Long pubAmount, Integer expireDate, Status status, String memo) {
        this.adAccountId = adAccountId;
        this.cashId = cashId;
        this.summary = summary;
        this.pubAmount = pubAmount;
        this.expireDate = expireDate;
        this.status = status;
        this.memo = memo;
    }
}