package com.adplatform.restApi.domain.adaccount.dto.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
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
            private Integer businessAccountId;

            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
        }

        @Getter
        @Setter
        public static class OutOfBalanceUpdate {
            private Integer id;
            private Boolean outOfBalance;
        }

        @Getter
        @Setter
        public static class ForAgencySearch {
            private Integer id;
            private String name;
//            private Boolean prePayment;
//            private AdAccount.CompanyType companyType;
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
        public static class ForAdminSearch {
            private Integer id;
            private BusinessAccount businessAccount;
            private String name;
            private String marketerName;
            private WalletDto.Response.WalletSpend walletSpend;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;

            @QueryProjection
            public ForAdminSearch(
                    Integer id,
                    BusinessAccount businessAccount,
                    String name,
                    String marketerName,
                    WalletDto.Response.WalletSpend walletSpend,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance) {
                this.id = id;
                this.businessAccount = businessAccount;
                this.name = name;
                this.marketerName = marketerName;
                this.walletSpend = walletSpend;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
            }
        }
        @Getter
        @Setter
        public static class ForAgencySearch {
            private Integer id;

            private BusinessAccount businessAccount;
            private String name;
            private String marketerName;
            private WalletDto.Response.WalletSpend walletSpend;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;

            @QueryProjection
            public ForAgencySearch(
                    Integer id,
                    BusinessAccount businessAccount,
                    String name,
                    String marketerName,
                    WalletDto.Response.WalletSpend walletSpend,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance) {
                this.id = id;
                this.businessAccount = businessAccount;
                this.name = name;
                this.marketerName = marketerName;
                this.walletSpend = walletSpend;
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
            private AdAccountUser.Status status;

            @QueryProjection
            public ForAdvertiserSearch(
                    Integer id,
                    String name,
                    AdAccount.Config config,
                    boolean adminStop,
                    boolean outOfBalance,
                    AdAccountUser.Status status) {
                this.id = id;
                this.name = name;
                this.config = config;
                this.adminStop = adminStop;
                this.outOfBalance = outOfBalance;
                this.status = status;
            }
        }
        @Getter
        @Setter
        public static class ForCashSearch {
            private Integer id;
            private String name;
            private AdAccount.Config config;
            private boolean adminStop;
            private boolean outOfBalance;

            @QueryProjection
            public ForCashSearch(
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
        @Getter
        @Setter
        public static class AdAccountCount {
            private long statusYCount;
            private long statusNCount;

            @QueryProjection
            public AdAccountCount(long statusYCount, long statusNCount) {
                this.statusYCount = statusYCount;
                this.statusNCount = statusNCount;
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

        @Getter
        @Setter
        public static class AdAccountCashInfo {
            private Long amount;
            private Long availableAmount;
            private Long reserveAmount;

            @QueryProjection
            public AdAccountCashInfo(
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
        public static class AdAccountCashDetailInfo {

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
            public AdAccountCashDetailInfo(
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
