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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wallet_log")
public class WalletLog extends BaseCreatedEntity {

    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "cash_info_id")
    private Integer cashId;
    @Column(name = "summary", length = 40)
    private String summary;

    @Column(name = "out_amount", columnDefinition = "INT")
    private Long outAmount;

    @Column(name = "in_amount", columnDefinition = "INT")
    private Long inAmount;

    @Column(name = "memo", length = 100)
    private String memo;

    @Builder
    public WalletLog(Integer adAccountId, Integer cashId, String summary, Long outAmount, Long inAmount, String memo) {
        this.adAccountId = adAccountId;
        this.cashId = cashId;
        this.summary = summary;
        this.outAmount = outAmount;
        this.inAmount = inAmount;
        this.memo = memo;
    }
}
