package com.adplatform.restApi.domain.wallet.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class WalletDto {
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
    }
}
