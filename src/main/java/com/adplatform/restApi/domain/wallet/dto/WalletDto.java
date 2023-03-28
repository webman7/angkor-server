package com.adplatform.restApi.domain.wallet.dto;

import com.adplatform.restApi.domain.wallet.domain.WalletFreeCash;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author junny
 * @since 1.0
 */
public class WalletDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveCash {
            @NotNull
            private int businessAccountId;
            private int adAccountId;
            private Float inAmount;
            private Float outAmount;
            private Long pubAmount;
            private String summary;
            private String memo;
        }

        @Getter
        @Setter
        public static class SaveWalletReserveLog {
            @NotNull
            private int businessAccountId;
            private int adAccountId;
            private int campaignId;
            private String fluctuation;
            private Float totalReserveAmount;
            private Float reserveAmount;
            private Float reserveVatAmount;
        }

        @Getter
        @Setter
        public static class SaveWalletLog {
            @NotNull
            private int businessAccountId;
            private int adAccountId;
            private String summary;
            private Float inAmount;
            private Float outAmount;
            private String memo;
            private int walletChargeLogId;
            private int walletAutoChargeLogId;
            private int walletRefundId;
        }

        @Getter
        @Setter
        public static class SaveFreeCash {
            @NotNull
            private int adAccountId;
            @NotNull
            private int cashId;
            private Float pubAmount;
            private int expireDate;
            private String summary;
            private String status;
            private String memo;
        }

        @Getter
        @Setter
        public static class FreeCashSearch {
            private int adAccountId;
            private String status;
            private String startDate;
            private String endDate;
        }

        @Getter
        @Setter
        public static class CashSearch {
            private int adAccountId;
            private String summary;
            private String startDate;
            private String endDate;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class WalletSpend {
            private Float cash;
            private Float todaySpend;
            private Float yesterdaySpend;
            private Float monthSpend;

            @QueryProjection
            public WalletSpend(Float cash, Float todaySpend, Float yesterdaySpend, Float monthSpend) {
                this.cash = cash;
                this.todaySpend = todaySpend;
                this.yesterdaySpend = yesterdaySpend;
                this.monthSpend = monthSpend;
            }
        }

        @Getter
        @Setter
        public static class WalletBalance {
            private Float cash;
            @QueryProjection
            public WalletBalance(Float cash) {
                this.cash = cash;
            }
        }

        @Getter
        @Setter
        public static class NewTradeNo {
            private int tradeNo;

            @QueryProjection
            public NewTradeNo(int tradeNo) {
                this.tradeNo = tradeNo;
            }
        }

//        @Getter
//        @Setter
//        public static class WalletCashTotal {
//            private int cashId;
//            private Float amount;
//            private Float availableAmount;
//            private Float reserveAmount;
//
//            @QueryProjection
//            public WalletCashTotal(Integer cashId, Float amount, Float availableAmount, Float reserveAmount) {
//                this.cashId = cashId;
//                this.amount = amount;
//                this.availableAmount = availableAmount;
//                this.reserveAmount = reserveAmount;
//            }
//        }

        @Getter
        @Setter
        public static class WalletMaster {
            private Float availableAmount;
            private Float totalReserveAmount;

            @QueryProjection
            public WalletMaster(Float availableAmount, Float totalReserveAmount) {
                this.availableAmount = availableAmount;
                this.totalReserveAmount = totalReserveAmount;
            }
        }

//        @Getter
//        @Setter
//        public static class FreeCashSearch {
//            private int id;
//            private int adAccountId;
//            private int cashId;
//            private String summary;
//            private Float pubAmount;
//            private int expireDate;
//            private WalletFreeCash.Status status;
//            private String memo;
//            private int createdUserNo;
//            private String createdUserId;
//            private LocalDateTime createdAt;
//            private int updatedUserNo;
//            private String updatedUserId;
//            private LocalDateTime updatedAt;
//
//            @QueryProjection
//            public FreeCashSearch(int id, int adAccountId, String summary, Float pubAmount, int expireDate, WalletFreeCash.Status status, String memo, int createdUserNo, String createdUserId, LocalDateTime createdAt, int updatedUserNo, String updatedUserId, LocalDateTime updatedAt) {
//                this.id = id;
//                this.adAccountId = adAccountId;
//                this.summary = summary;
//                this.pubAmount = pubAmount;
//                this.expireDate = expireDate;
//                this.status = status;
//                this.memo = memo;
//                this.createdUserNo = createdUserNo;
//                this.createdUserId = createdUserId;
//                this.createdAt = createdAt;
//                this.updatedUserNo = updatedUserNo;
//                this.updatedUserId = updatedUserId;
//                this.updatedAt = updatedAt;
//            }
//
//        }

        @Getter
        @Setter
        public static class CashSearch {
            private int id;
            private int businessAccountId;
            private int adAccountId;
            private String summary;
            private Float inAmount;
            private Float outAmount;
            private String memo;
            private int createdUserNo;
            private String createdUserId;
            private LocalDateTime createdAt;
            @QueryProjection
            public CashSearch(int id, int businessAccountId, int adAccountId, String summary, Float inAmount, Float outAmount, String memo, int createdUserNo, String createdUserId, LocalDateTime createdAt) {
                this.id = id;
                this.businessAccountId = businessAccountId;
                this.adAccountId = adAccountId;
                this.summary = summary;
                this.inAmount = inAmount;
                this.outAmount = outAmount;
                this.memo = memo;
                this.createdUserNo = createdUserNo;
                this.createdUserId = createdUserId;
                this.createdAt = createdAt;
            }
        }
    }
}
