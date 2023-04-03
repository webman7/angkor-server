package com.adplatform.restApi.domain.business.domain;

import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "business_account_info")
@DynamicInsert
public class BusinessAccount extends BaseUpdatedEntity {
    /**
     * 광고 타입
     */
    public enum PaymentType {
        /** 계좌이체 */
        prepayment,
        /** 신용카드 */
        creditcard
    }

    public enum Config {
        ON, OFF, DEL
    }

    @OneToMany(mappedBy = "businessAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BusinessAccountUser> businessAccountUsers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_info_id")
    private Company company;

    @OneToOne(mappedBy = "businessAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletMaster walletMaster;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType type;

    @Column(name = "name")
    private String name;

    @Column(name = "credit_limit", columnDefinition = "INT")
    private Integer creditLimit;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "pre_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean prePayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "config", nullable = false, columnDefinition = "CHAR(5)")
    private Config config;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "out_of_balance_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean outOfBalance;

    @Builder
    public BusinessAccount(
            Company company,
            PaymentType type,
            String name,
            Integer creditLimit,
            boolean prePayment,
            Config config,
            boolean outOfBalance) {
        this.company = company;
        this.type = type;
        this.name = name;
        this.creditLimit = creditLimit;
        this.prePayment = prePayment;
        this.config = config;
        this.outOfBalance = outOfBalance;
    }

    public BusinessAccount update(BusinessAccountDto.Request.Update request) {
        this.name = request.getName();
        return this;

    }

    public BusinessAccount addBusinessAccountUser(User user, BusinessAccountUser.MemberType memberType, BusinessAccountUser.AccountingYN accountingYN, BusinessAccountUser.Status status) {
        this.businessAccountUsers.add(new BusinessAccountUser(this, user, memberType, accountingYN, status));
        return this;
    }

    public BusinessAccount changeWalletMaster(WalletMaster walletMaster) {
        this.walletMaster = walletMaster.updateBusinessAccount(this);
        return this;
    }

    public void creditLimitUpdate(BusinessAccountDto.Request.CreditLimitUpdate request) {
        this.creditLimit = request.getCreditLimit();
    }

    public void changeConfigOn() {
        this.config = BusinessAccount.Config.ON;
    }

    public void changeConfigOff() {
        this.config = BusinessAccount.Config.OFF;
    }
}
