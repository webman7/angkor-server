package com.adplatform.restApi.domain.wallet.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class WalletDto {
    public static abstract class Response {
        @Getter
        @Setter
        public static class WalletSpend {
            private int cash;
            private int todaySpend;
            private int yesterdaySpend;
            private int monthSpend;

            @QueryProjection
            public WalletSpend(int cash, int todaySpend, int yesterdaySpend, int monthSpend) {
                this.cash = cash;
                this.todaySpend = todaySpend;
                this.yesterdaySpend = yesterdaySpend;
                this.monthSpend = monthSpend;
            }
        }
    }
}
