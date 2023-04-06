package com.adplatform.restApi.domain.business.dto.account;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.statistics.domain.taxbill.BusinessAccountTaxBill;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.global.value.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author junny
 * @since 1.0
 */
public class BusinessAccountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Accounts {
            private Integer id;
            private String name;
            private Integer loginUserNo;
        }

        @Getter
        @Setter
        public static class BusinessAdAccount {
            private String searchType;
            private String searchKeyword;
        }

        @Getter
        @Setter
        public static class SearchCredit {
            private Integer id;
            private String name;
        }

        @Getter
        @Setter
        public static class UpdateIssue {
            private Integer id;
        }

        @Getter
        @Setter
        public static class SearchTax {
            private String searchType;
            private String searchKeyword;
            private Integer startDate;
            private Integer endDate;
        }

        @Getter
        @Setter
        public static class UpdateRefundAccount {
            private Integer businessAccountId;
            private Integer bankId;
            private String accountNumber;
            private String accountOwner;
        }

        @Getter
        @Setter
        public static class Save {

            @Size(min = 1, max = 50)
            @NotBlank
            private String name;

            @Size(min = 1, max = 50)
            private String companyName;

            private Integer companyId;

            @NotBlank
            @Size(min = 1, max = 20)
            private String registrationNumber;

            @NotBlank
            @Size(min = 1, max = 50)
            private String representationName;

            @NotBlank
            @Size(min = 1, max = 20)
            private String businessCategory;

            @NotBlank
            @Size(min = 1, max = 50)
            private String businessItem;
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            @NotNull
            @Email
            private String taxBillEmail;
        }

        @Getter
        @Setter
        public static class Update {

            private Integer id;
            @Size(min = 1, max = 50)
            private String name;

            @Size(min = 1, max = 50)
            private String companyName;

            private Integer companyId;

            @NotBlank
            @Size(min = 1, max = 20)
            private String registrationNumber;

            @NotBlank
            @Size(min = 1, max = 50)
            private String representationName;

            @NotBlank
            @Size(min = 1, max = 20)
            private String businessCategory;

            @NotBlank
            @Size(min = 1, max = 50)
            private String businessItem;

            private String baseAddress;
            private String detailAddress;
            private String zipCode;

            @NotNull
            @Email
            private String taxBillEmail;
        }










        @Getter
        @Setter
        public static class OutOfBalanceUpdate {
            private Integer id;
            private Boolean outOfBalance;
        }

        @Getter
        @Setter
        public static class CreditLimitUpdate {
            private Integer id;
            private Integer creditLimit;
        }

        @Getter
        @Setter
        public static class ForAgencySearch {
            private Integer id;
            private String name;
            private Boolean prePayment;
//            private BusinessAccount.CompanyType companyType;
//            private List<WalletDto.Request.WalletSpend> walletSpend;
//            private String config;
//            private String adminStopYn;
//            private String outOfBalanceYn;
        }

        @Getter
        @Setter
        public static class ForAdminSearch {
            private Integer id;
            private String name;
        }

        @Getter
        @Setter
        public static class ForCashSearch {
            private Integer id;
            private String name;
        }
    }


    public static abstract class Response {
        @Getter
        @Setter
        public static class Accounts {
            private Integer id;
            private String name;
            private String memberType;
            private Integer adAccountId;
            private String config;
            private String outOfBalance;
        }

        @Getter
        @Setter
        public static class BusinessAdAccount {
            private Integer id;
            private String name;
            private Integer adAccountId;
            private String adAccountName;
            private boolean adminStop;
        }


        @Getter
        @Setter
        public static class BusinessAccountCount {
            private long statusYCount;
            private long statusNCount;

            @QueryProjection
            public BusinessAccountCount(long statusYCount, long statusNCount) {
                this.statusYCount = statusYCount;
                this.statusNCount = statusNCount;
            }
        }
        @Getter
        @Setter
        public static class BusinessAccountInfo {
            private Integer id;
            private String name;
            private BusinessAccount.Config config;
            private CompanyInfo company;

            @QueryProjection
            public BusinessAccountInfo(
                    Integer id,
                    String name,
                    BusinessAccount.Config config,
                    CompanyInfo company) {
                this.id = id;
                this.name = name;
                this.config = config;
                this.company = company;
            }
        }


        @Getter
        @Setter
        public static class AdAccountBusinessAccountInfo {
            private Integer id;
            private String name;
            private BusinessAccount.Config config;
            @QueryProjection
            public AdAccountBusinessAccountInfo(
                    Integer id,
                    String name,
                    BusinessAccount.Config config) {
                this.id = id;
                this.name = name;
                this.config = config;
            }
        }

        @Getter
        @Setter
        public static class AdAccountInfo {
            private Integer id;
            private String name;
            @QueryProjection
            public AdAccountInfo(
                    Integer id,
                    String name) {
                this.id = id;
                this.name = name;
            }
        }

        @Getter
        @Setter
        public static class AdAccountMemberInfo {
            private Integer id;
            private String name;
            private AdAccountUser.MemberType memberType;

            @QueryProjection
            public AdAccountMemberInfo(
                    Integer id,
                    String name,
                    AdAccountUser.MemberType memberType) {
                this.id = id;
                this.name = name;
                this.memberType = memberType;
            }
        }

        @Getter
        @Setter
        public static class BusinessAccountCreditInfo {

            private Integer id;
            private String name;
            private Float availableAmount;
            private Float totalReserveAmount;
            private BusinessAccount.PaymentType type;
            private BusinessAccount.Config config;

            @QueryProjection
            public BusinessAccountCreditInfo(
                    Integer id,
                    String name,
                    Float availableAmount,
                    Float totalReserveAmount,
                    BusinessAccount.PaymentType type,
                    BusinessAccount.Config config) {
                this.id = id;
                this.name = name;
                this.availableAmount = availableAmount;
                this.totalReserveAmount = totalReserveAmount;
                this.type = type;
                this.config = config;
            }
        }

        @Getter
        @Setter
        public static class BusinessAccountTaxInfo {

            private Integer id;
            private Integer businessAccountId;
            private String businessAccountName;
            private Integer companyId;
            private String companyName;
            private Integer statDate;
            private Float supplyAmount;
            private Float vatAmount;
            private Float totalAmount;
            private boolean issueStatus;
            private Integer issueUserNo;
            private String issueUserId;
            private LocalDateTime issueDate;

            @QueryProjection
            public BusinessAccountTaxInfo(
                    Integer id,
                    Integer businessAccountId,
                    String businessAccountName,
                    Integer companyId,
                    String companyName,
                    Integer statDate,
                    Float supplyAmount,
                    Float vatAmount,
                    Float totalAmount,
                    boolean issueStatus,
                    Integer issueUserNo,
                    String issueUserId,
                    LocalDateTime issueDate) {
                this.id = id;
                this.businessAccountId = businessAccountId;
                this.businessAccountName = businessAccountName;
                this.companyId = companyId;
                this.companyName = companyName;
                this.statDate = statDate;
                this.supplyAmount = supplyAmount;
                this.vatAmount = vatAmount;
                this.totalAmount = totalAmount;
                this.issueStatus = issueStatus;
                this.issueUserNo = issueUserNo;
                this.issueUserId = issueUserId;
                this.issueDate = issueDate;
            }
        }

        @Getter
        @Setter
        public static class BusinessAccountCashInfo {
            private Float availableAmount;
            private Float totalReserveAmount;
            @QueryProjection
            public BusinessAccountCashInfo(
                    Float availableAmount,
                    Float totalReserveAmount) {
                this.availableAmount = availableAmount;
                this.totalReserveAmount = totalReserveAmount;
            }
        }

        @Getter
        @Setter
        public static class CompanyInfo {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            private String businessCategory;
            private String businessItem;
            private com.adplatform.restApi.global.value.Email taxBillEmail;
            @QueryProjection
            public CompanyInfo(Integer id,
                               String name,
                               Company.Type type,
                               String registrationNumber,
                               String representationName,
                               String baseAddress,
                               String detailAddress,
                               String zipCode,
                               String businessCategory,
                               String businessItem,
                               com.adplatform.restApi.global.value.Email taxBillEmail) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.baseAddress = baseAddress;
                this.detailAddress = detailAddress;
                this.zipCode = zipCode;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
            }
        }









        @Getter
        @Setter
        public static class ForAdminSearch {
            private Integer id;
            private String name;
            private String marketerName;
            private WalletDto.Response.WalletSpend walletSpend;
            private Integer creditLimit;
            private boolean prePayment;
            private BusinessAccount.Config config;

            @QueryProjection
            public ForAdminSearch(
                    Integer id,
                    String name,
                    String marketerName,
                    WalletDto.Response.WalletSpend walletSpend,
                    Integer creditLimit,
                    boolean prePayment,
                    BusinessAccount.Config config) {
                this.id = id;
                this.name = name;
                this.marketerName = marketerName;
                this.walletSpend = walletSpend;
                this.creditLimit = creditLimit;
                this.prePayment = prePayment;
                this.config = config;
            }
        }
        @Getter
        @Setter
        public static class ForAgencySearch {
            private Integer id;
            private String name;
            private String marketerName;
            private WalletDto.Response.WalletSpend walletSpend;
            private Integer creditLimit;
            private boolean prePayment;
            private BusinessAccount.Config config;

            @QueryProjection
            public ForAgencySearch(
                    Integer id,
                    String name,
                    String marketerName,
                    WalletDto.Response.WalletSpend walletSpend,
                    Integer creditLimit,
                    boolean prePayment,
                    BusinessAccount.Config config) {
                this.id = id;
                this.name = name;
                this.marketerName = marketerName;
                this.walletSpend = walletSpend;
                this.creditLimit = creditLimit;
                this.prePayment = prePayment;
                this.config = config;
            }
        }

        @Getter
        @Setter
        public static class ForAdvertiserSearch {
            private Integer id;
            private String name;
            private String masterEmail;
            private long memberSize;
            private BusinessAccount.Config config;
            private BusinessAccountUser.Status status;

            @QueryProjection
            public ForAdvertiserSearch(
                    Integer id,
                    String name,
                    String masterEmail,
                    long memberSize,
                    BusinessAccount.Config config,
                    BusinessAccountUser.Status status) {
                this.id = id;
                this.name = name;
                this.masterEmail = masterEmail;
                this.memberSize = memberSize;
                this.config = config;
                this.status = status;
            }
        }
        @Getter
        @Setter
        public static class ForCashSearch {
            private Integer id;
            private String name;
            private String marketerName;
            private WalletDto.Response.WalletBalance walletBalance;
            private Integer creditLimit;
            private boolean prePayment;
            private BusinessAccount.Config config;

            @QueryProjection
            public ForCashSearch(
                    Integer id,
                    String name,
                    String marketerName,
                    WalletDto.Response.WalletBalance walletBalance,
                    Integer creditLimit,
                    boolean prePayment,
                    BusinessAccount.Config config) {
                this.id = id;
                this.name = name;
                this.marketerName = marketerName;
                this.walletBalance = walletBalance;
                this.creditLimit = creditLimit;
                this.prePayment = prePayment;
                this.config = config;
            }
        }

    }
}
