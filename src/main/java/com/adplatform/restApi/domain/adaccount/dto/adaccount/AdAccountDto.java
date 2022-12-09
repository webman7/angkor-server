package com.adplatform.restApi.domain.adaccount.dto.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @NotNull
            private AdAccount.AdAccountType type;
            @NotBlank
            private String businessRegistrationNumber;
            @Size(min = 1, max = 20)
            @NotBlank
            private String name;
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
            private Boolean preDeferredPayment;
//            private AdAccount.CompanyType companyType;
//            private List<WalletDto.Request.WalletSpend> walletSpend;
//            private String config;
//            private String adminStopYn;
//            private String outOfBalanceYn;
        }
    }


    public static abstract class Response {
        @Getter
        @Setter
        public static class ForAgencySearch {
            private Integer id;
            private String name;
            private String marketerName;
            private Company.Type companyType;
            private WalletDto.Response.WalletSpend walletSpend;
            private Integer creditLimit;
            private boolean preDeferredPayment;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;

            @QueryProjection
            public ForAgencySearch(
                    Integer id,
                    String name,
                    String marketerName,
                    Company.Type companyType,
                    WalletDto.Response.WalletSpend walletSpend,
                    Integer creditLimit,
                    boolean preDeferredPayment,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance) {
                this.id = id;
                this.name = name;
                this.marketerName = marketerName;
                this.companyType = companyType;
                this.walletSpend = walletSpend;
                this.creditLimit = creditLimit;
                this.preDeferredPayment = preDeferredPayment;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
            }
        }

        @Getter
        @Setter
        public static class ForAdvertiserSearch {
            private Integer id;
            private String name;
            private String masterEmail;
            private long memberSize;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;
            private AdAccountUser.RequestStatus requestStatus;

            @QueryProjection
            public ForAdvertiserSearch(
                    Integer id,
                    String name,
                    String masterEmail,
                    long memberSize,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance,
                    AdAccountUser.RequestStatus requestStatus) {
                this.id = id;
                this.name = name;
                this.masterEmail = masterEmail;
                this.memberSize = memberSize;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
                this.requestStatus = requestStatus;
            }
        }
        @Getter
        @Setter
        public static class AdAccountCount {
            private long requestStatusYCount;
            private long requestStatusNCount;

            @QueryProjection
            public AdAccountCount(long requestStatusYCount, long requestStatusNCount) {
                this.requestStatusYCount = requestStatusYCount;
                this.requestStatusNCount = requestStatusNCount;
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

            @QueryProjection
            public AdAccountInfo(
                    Integer id,
                    String name,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance) {
                this.id = id;
                this.name = name;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
            }
        }
    }
}
