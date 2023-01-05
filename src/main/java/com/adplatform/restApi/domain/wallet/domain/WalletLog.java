package com.adplatform.restApi.domain.wallet.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.statistics.domain.report.ReportAdGroupDailyId;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.*;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(WalletLogId.class)
@Entity
@Table(name = "wallet_log")
public class WalletLog {

//    @EmbeddedId
//    private final WalletLogId id = new WalletLogId();

    @Id
    @Column(name = "adaccount_info_id")
    private int id;

    @Id
    @Column(name = "trade_no")
    private int tradeNo;

    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "out_amount", columnDefinition = "INT")
    private Long outAmount;

    @Column(name = "in_amount", columnDefinition = "INT")
    private Long inAmount;

    @Column(name = "balance", columnDefinition = "INT")
    private Long balance;

    @Column(name = "memo", length = 100)
    private String memo;

    @CreatedBy
    @Column(name = "reg_user_no")
    private Integer createdUserId;

    @Builder
    public WalletLog(Integer id, Integer tradeNo, String summary, Long outAmount, Long inAmount, Long balance, String memo, Integer createdUserId) {
        this.id = id;
        this.tradeNo = tradeNo;
        this.summary = summary;
        this.outAmount = outAmount;
        this.inAmount = inAmount;
        this.balance = balance;
        this.memo = memo;
        this.createdUserId = createdUserId;
    }
}
