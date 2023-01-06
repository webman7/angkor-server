package com.adplatform.restApi.domain.wallet.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class WalletDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @NotNull
            private int id;
            @NotNull
            private int cashId;
            private int tradeNo;
            private Long inAmount;
            private Long outAmount;
            private Long balance;
            private String summary;
            private String memo;
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
    }
}
