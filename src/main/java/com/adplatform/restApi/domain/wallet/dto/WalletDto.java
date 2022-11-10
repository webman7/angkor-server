package com.adplatform.restApi.domain.wallet.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class WalletDto {
    public static abstract class Response {
        @Getter
        @Setter
        public static class WalletSpend {
            private Integer cash;
//            private Integer todaySpend;
//            private Integer yesterdaySpend;
//            private Integer monthSpend;
            @QueryProjection
            public WalletSpend(Integer cash) {
                this.cash = cash;
            }
        }
    }
}
