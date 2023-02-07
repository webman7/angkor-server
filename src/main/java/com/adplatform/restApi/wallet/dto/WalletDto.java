package com.adplatform.restApi.wallet.dto;

import com.adplatform.restApi.wallet.domain.WalletFreeCash;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class WalletDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveCash {
            @NotNull
            private int adAccountId;
            @NotNull
            private int cashId;
            private Long inAmount;
            private Long outAmount;
            private Long pubAmount;
            private String summary;
            private String memo;
        }

        @Getter
        @Setter
        public static class SaveFreeCash {
            @NotNull
            private int adAccountId;
            @NotNull
            private int cashId;
            private Long pubAmount;
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
            private Long cash;
            private int todaySpend;
            private int yesterdaySpend;
            private int monthSpend;

            @QueryProjection
            public WalletSpend(Long cash, int todaySpend, int yesterdaySpend, int monthSpend) {
                this.cash = cash;
                this.todaySpend = todaySpend;
                this.yesterdaySpend = yesterdaySpend;
                this.monthSpend = monthSpend;
            }
        }

        @Getter
        @Setter
        public static class WalletBalance {
            private Long cash;
            private Long freeCash;

            @QueryProjection
            public WalletBalance(Long cash, Long freeCash) {
                this.cash = cash;
                this.freeCash = freeCash;
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

        @Getter
        @Setter
        public static class WalletCashTotal {
            private int cashId;
            private Long amount;
            private Long availableAmount;
            private Long reserveAmount;

            @QueryProjection
            public WalletCashTotal(Integer cashId, Long amount, Long availableAmount, Long reserveAmount) {
                this.cashId = cashId;
                this.amount = amount;
                this.availableAmount = availableAmount;
                this.reserveAmount = reserveAmount;
            }
        }

        @Getter
        @Setter
        public static class FreeCashSearch {
            private int id;
            private int adAccountId;
            private int cashId;
            private String summary;
            private Long pubAmount;
            private int expireDate;
            private WalletFreeCash.Status status;
            private String memo;
            private int createdUserNo;
            private String createdUserId;
            private LocalDateTime createdAt;
            private int updatedUserNo;
            private String updatedUserId;
            private LocalDateTime updatedAt;

            @QueryProjection
            public FreeCashSearch(int id, int adAccountId, int cashId, String summary, Long pubAmount, int expireDate, WalletFreeCash.Status status, String memo, int createdUserNo, String createdUserId, LocalDateTime createdAt, int updatedUserNo, String updatedUserId, LocalDateTime updatedAt) {
                this.id = id;
                this.adAccountId = adAccountId;
                this.cashId = cashId;
                this.summary = summary;
                this.pubAmount = pubAmount;
                this.expireDate = expireDate;
                this.status = status;
                this.memo = memo;
                this.createdUserNo = createdUserNo;
                this.createdUserId = createdUserId;
                this.createdAt = createdAt;
                this.updatedUserNo = updatedUserNo;
                this.updatedUserId = updatedUserId;
                this.updatedAt = updatedAt;
            }

        }

        @Getter
        @Setter
        public static class CashSearch {
            private int id;
            private int adAccountId;
            private int cashId;
            private String summary;
            private Long inAmount;
            private Long outAmount;
            private String memo;
            private int createdUserNo;
            private String createdUserId;
            private LocalDateTime createdAt;
            @QueryProjection
            public CashSearch(int id, int adAccountId, int cashId, String summary, Long inAmount, Long outAmount, String memo, int createdUserNo, String createdUserId, LocalDateTime createdAt) {
                this.id = id;
                this.adAccountId = adAccountId;
                this.cashId = cashId;
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
