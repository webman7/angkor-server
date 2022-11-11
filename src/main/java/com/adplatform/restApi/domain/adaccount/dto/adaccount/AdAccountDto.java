package com.adplatform.restApi.domain.adaccount.dto.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class AdAccountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private AdAccount.AdAccountType type;
            private String businessRegistrationNumber;
            private String name;
        }

        @Getter
        @Setter
        public static class MySearch {
            private Integer id;
            private String name;
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
        public static class Page {
            private Integer id;
            private String name;
            private WalletDto.Response.WalletSpend walletSpend;
            private Integer creditLimit;
            private boolean preDeferredPayment;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;

            @QueryProjection
            public Page(
                    Integer id,
                    String name,
                    WalletDto.Response.WalletSpend walletSpend,
                    Integer creditLimit,
                    boolean preDeferredPayment,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance) {
                this.id = id;
                this.name = name;
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
        public static class ForAdvertiser {
            private Integer id;
            private String name;
            private String masterEmail;
            private long memberSize;
            private AdAccount.Config config;
            private boolean outOfBalance;
            private AdAccountUser.RequestStatus requestStatus;

            @QueryProjection
            public ForAdvertiser(
                    Integer id,
                    String name,
                    String masterEmail,
                    long memberSize,
                    AdAccount.Config config,
                    boolean outOfBalance,
                    AdAccountUser.RequestStatus requestStatus) {
                this.id = id;
                this.name = name;
                this.masterEmail = masterEmail;
                this.memberSize = memberSize;
                this.config = config;
                this.outOfBalance = outOfBalance;
                this.requestStatus = requestStatus;
            }
        }
    }
}
