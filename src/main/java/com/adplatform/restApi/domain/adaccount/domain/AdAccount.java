package com.adplatform.restApi.domain.adaccount.domain;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountQuerydslRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_info")
public class AdAccount extends BaseUpdatedEntity {
    /**
     * 회사 타입
     */
    public enum CompanyType {
        /** 대행사 */
        AGENCY,
        /** 광고주 */
        ADVERTISER
    }

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

    @Column(name = "company_info_id", columnDefinition = "Integer")
    private Integer companyInfoId;

    @Column(name = "owner_company_info_id", columnDefinition = "Integer")
    private Integer ownerCompanyInfoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "adaccount_type")
    private AdAccount.AdAccountType adAccountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform_type")
    private AdAccount.PlatformType platformType;


    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private AdAccount.CompanyType companyType;

    @Column(name = "business_registration_number", length = 20)
    private String businessRegistrationNumber;

    @Column(name = "member_count")
    private Integer memberCount;

    @Column(name = "business_right_yn", nullable = false, columnDefinition = "CHAR(1)")
    private String businessRightYn;

    @Column(name = "credit_limit")
    private Integer creditLimit;

    @Column(name = "pre_deferred_payment_yn", nullable = false, columnDefinition = "CHAR(1)")
    private String preDeferredPaymentYn;

    @Column(name = "repayment_criteria", length = 10)
    private String repaymentCriteria;

    @Column(name = "config", nullable = false, columnDefinition = "CHAR(5)")
    private String config;

    @Column(name = "admin_stop_yn", nullable = false, columnDefinition = "CHAR(1)")
    private String adminStopYn;

    @Column(name = "out_of_balance_yn", nullable = false, columnDefinition = "CHAR(1)")
    private String outOfBalanceYn;

}
