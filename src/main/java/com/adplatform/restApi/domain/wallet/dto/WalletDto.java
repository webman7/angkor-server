package com.adplatform.restApi.domain.wallet.dto;

import lombok.Getter;
import lombok.Setter;

public class WalletDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class WalletSpend {
            private Integer cash;
            private Integer todaySpend;
            private Integer yesterdaySpend;
            private Integer monthSpend;
        }
    }
}
