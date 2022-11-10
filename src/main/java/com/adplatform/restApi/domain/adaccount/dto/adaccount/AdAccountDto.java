package com.adplatform.restApi.domain.adaccount.dto.adaccount;

import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AdAccountDto {

    public static abstract class Request {

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
            private List<WalletDto.Request.WalletSpend> walletSpend;
            private Integer creditLimit;
            private String preDeferredPaymentYn;
            private String config;
            private String adminStopYn;
            private String outOfBalanceYn;

            @QueryProjection
            public Page(
                    Integer id,
                    String name,
                    List<WalletDto.Request.WalletSpend> walletSpend,
                    Integer creditLimit,
                    String preDeferredPaymentYn,
                    String config,
                    String adminStopYn,
                    String outOfBalanceYn) {
                this.id = id;
                this.name = name;
                this.walletSpend = walletSpend;
                this.creditLimit = creditLimit;
                this.preDeferredPaymentYn = preDeferredPaymentYn;
                this.config = config;
                this.adminStopYn = adminStopYn;
                this.outOfBalanceYn = outOfBalanceYn;
            }
        }

    }
}
