package com.adplatform.restApi.domain.statistics.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class SaleAmountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private Integer statDate;
            private Integer adAccountId;
            private Integer cashId;
            private Integer saleAmount;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Integer statDate;
        private Integer adAccountId;
        private Integer cashId;
        private Integer saleAmount;
    }
}
