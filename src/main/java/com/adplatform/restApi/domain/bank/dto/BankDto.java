package com.adplatform.restApi.domain.bank.dto;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BankDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
        }

        @Getter
        @Setter
        public static class Update {

            private Integer id;

            @Size(min = 1, max = 50)
            @NotBlank
            private String name;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class BankInfo {

            private Integer id;

            @Size(min = 1, max = 50)
            @NotBlank
            private String name;

            @QueryProjection
            public BankInfo(
                    Integer id,
                    String name) {
                this.id = id;
                this.name = name;
            }
        }
    }
}
