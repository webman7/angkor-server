package com.adplatform.restApi.domain.business.dto.account;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
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
        public static class Accounts {
            private Integer id;
            private String name;
            private String memberType;
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
            private CompanyDto.Response.CompanyInfo company;

            @QueryProjection
            public BusinessAccountInfo(
                    Integer id,
                    String name,
                    BusinessAccount.Config config,
                    CompanyDto.Response.CompanyInfo company) {
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
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;
            private BusinessAccountDto.Response.AdAccountBusinessAccountInfo business;

            @QueryProjection
            public AdAccountInfo(
                    Integer id,
                    String name,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance,
                    BusinessAccountDto.Response.AdAccountBusinessAccountInfo business) {
                this.id = id;
                this.name = name;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
                this.business = business;
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
