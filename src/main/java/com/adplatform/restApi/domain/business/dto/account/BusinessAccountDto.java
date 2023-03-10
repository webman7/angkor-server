package com.adplatform.restApi.domain.business.dto.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.global.value.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class BusinessAccountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class MyAccounts {
            private Integer id;
            private String name;
            private Integer loginUserNo;
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

            private Address address;

            @NotNull
            @Email
            private String taxBillEmail;
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
        public static class MyAccounts {
            private Integer id;
            private String name;
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

            @QueryProjection
            public BusinessAccountInfo(
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
        public static class BusinessAccountCashInfo {
            private Long amount;
            private Long availableAmount;
            private Long reserveAmount;

            @QueryProjection
            public BusinessAccountCashInfo(
                    Long amount,
                    Long availableAmount,
                    Long reserveAmount) {
                this.amount = amount;
                this.availableAmount = availableAmount;
                this.reserveAmount = reserveAmount;
            }
        }

        @Getter
        @Setter
        public static class BusinessAccountCashDetailInfo {

            private Integer id;
            private String name;
            private Boolean saleAffect;
            private Boolean refund;
            private Integer priority;
            private Integer cashId;
            private Long amount;
            private Long availableAmount;
            private Long reserveAmount;

            @QueryProjection
            public BusinessAccountCashDetailInfo(
                    Integer id,
                    String name,
                    Boolean saleAffect,
                    Boolean refund,
                    Integer priority,
                    Integer cashId,
                    Long amount,
                    Long availableAmount,
                    Long reserveAmount) {
                this.id = id;
                this.name = name;
                this.saleAffect = saleAffect;
                this.refund = refund;
                this.priority = priority;
                this.cashId = cashId;
                this.amount = amount;
                this.availableAmount = availableAmount;
                this.reserveAmount = reserveAmount;
            }
        }
    }
}
