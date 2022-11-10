package com.adplatform.restApi.domain.adaccount.domain;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_info")
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

    @OneToMany(mappedBy = "adAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdAccountUser> adAccountUsers = new ArrayList<>();

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

    @Column(name = "member_count")
    private Integer memberCount;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "business_right_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean businessRight;

    @Column(name = "credit_limit")
    private Integer creditLimit;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "pre_deferred_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean preDeferredPayment;

    @Column(name = "repayment_criteria", length = 10)
    private String repaymentCriteria;

    @Column(name = "config", nullable = false, columnDefinition = "CHAR(5)")
    private String config;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "admin_stop_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean adminStop;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "out_of_balance_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean outOfBalance;
}
