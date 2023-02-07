package com.adplatform.restApi.adaccount.domain;

import com.adplatform.restApi.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.company.domain.Company;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.wallet.domain.Cash;
import com.adplatform.restApi.wallet.domain.WalletMaster;
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
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_info")
@DynamicInsert
public class AdAccount extends BaseUpdatedEntity {
    /**
     * 광고 타입
     */
    public enum AdAccountType {
        /** 회사 */
        BUSINESS,
        /** 개인 */
        INDIVIDUAL
    }

    /**
     * 플랫폼 타입
     */
    public enum PlatformType {
        /** 디스플레이 광고 */
        AD
    }

    public enum Config {
        ON, OFF, DEL
    }

    @OneToMany(mappedBy = "adAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AdAccountUser> adAccountUsers = new ArrayList<>();

    @OneToMany(mappedBy = "adAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Campaign> campaigns = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_info_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "owner_company_info_id")
    private Company ownerCompany;

    @OneToOne(mappedBy = "adAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletMaster walletMaster;

    @Enumerated(EnumType.STRING)
    @Column(name = "adaccount_type")
    private AdAccount.AdAccountType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform_type")
    private AdAccount.PlatformType platformType;

    @Column(name = "name")
    private String name;

    @Column(name = "business_registration_number", length = 20)
    private String businessRegistrationNumber;

    @Column(name = "tax_bill_registration_number", length = 20)
    private String taxBillRegistrationNumber;

    @Column(name = "member_count", columnDefinition = "INT")
    private Integer memberCount;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "agency_register_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean agencyRegister;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "business_right_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean businessRight;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "request_business_right_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean requestBusinessRight;

    @Column(name = "credit_limit", columnDefinition = "INT")
    private Integer creditLimit;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "pre_deferred_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean preDeferredPayment;

    @Column(name = "repayment_criteria", length = 10)
    private String repaymentCriteria;

    @Enumerated(EnumType.STRING)
    @Column(name = "config", nullable = false, columnDefinition = "CHAR(5)")
    private Config config;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "admin_stop_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean adminStop;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "out_of_balance_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean outOfBalance;

    @Builder
    public AdAccount(
            User user,
            Company ownerCompany,
            AdAccountType type,
            PlatformType platformType,
            String name,
            String businessRegistrationNumber,
            String taxBillRegistrationNumber,
            boolean agencyRegister,
            boolean businessRight,
            boolean requestBusinessRight,
            Integer creditLimit,
            boolean preDeferredPayment,
            Config config,
            boolean adminStop,
            boolean outOfBalance) {
        this.company = user.getCompany();
        this.ownerCompany = ownerCompany;
        this.type = type;
        this.platformType = platformType;
        this.name = name;
        this.businessRegistrationNumber = businessRegistrationNumber;
        this.taxBillRegistrationNumber = taxBillRegistrationNumber;
        this.agencyRegister = agencyRegister;
        this.businessRight = businessRight;
        this.requestBusinessRight = requestBusinessRight;
        this.creditLimit = creditLimit;
        this.preDeferredPayment = preDeferredPayment;
        this.config = config;
        this.adminStop = adminStop;
        this.outOfBalance = outOfBalance;
    }

    public AdAccount addAdAccountUser(User user, AdAccountUser.MemberType memberType, AdAccountUser.RequestStatus requestStatus) {
        this.adAccountUsers.add(new AdAccountUser(this, user, memberType, requestStatus));
        return this;
    }

    public AdAccount changeWalletMaster(WalletMaster walletMaster, List<Cash> cashes) {
        this.walletMaster = walletMaster.updateAdAccount(this).initWalletCashTotal(cashes);
        return this;
    }

    public void creditLimitUpdate(AdAccountDto.Request.CreditLimitUpdate request) {
        this.creditLimit = request.getCreditLimit();
    }

    public AdAccount outOfBalanceUpdate(AdAccountDto.Request.OutOfBalanceUpdate request) {
        this.outOfBalance = request.getOutOfBalance();
        return this;
    }

    public void changeConfigOn() {
        this.config = AdAccount.Config.ON;
    }

    public void changeConfigOff() {
        this.config = AdAccount.Config.OFF;
    }
}
