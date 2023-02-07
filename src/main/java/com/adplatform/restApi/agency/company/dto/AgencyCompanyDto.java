package com.adplatform.restApi.agency.company.dto;

import com.adplatform.restApi.advertiser.adaccount.domain.AdAccount;
import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.wallet.dto.WalletDto;
import com.adplatform.restApi.global.value.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public abstract class AgencyCompanyDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
            private String platformType;
            private String searchType;
            private String searchKeyword;
            private Integer currDate;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class SpendSummary {
            private String platformType;
            private Integer prePaymentCnt;
            private Integer deferredPaymentCnt;
            private Integer todaySpend;
            private Integer yesterdaySpend;
            private Integer monthSpend;
        }

        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private String name;
            private Integer userId;
            private String loginId;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private Address address;
            private String businessCategory;
            private String businessItem;
            private String taxBillEmail1;
            private String taxBillEmail2;
            private boolean active;
            private boolean deleted;
        }

        @Getter
        @Setter
        public static class SearchForAdmin {
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
            public SearchForAdmin(
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

    }
}
