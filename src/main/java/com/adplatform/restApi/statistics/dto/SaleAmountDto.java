package com.adplatform.restApi.statistics.dto;

import lombok.Getter;
import lombok.Setter;

public class SaleAmountDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private Integer statDate;
            private Integer adAccountId;
            private Integer cashId;
            private Integer companyId;
            private Integer ownerCompanyId;
            private Integer saleAmount;
            private Integer remainAmount;
        }
    }

    @Getter
    @Setter
    public static class Response {
        private Integer statDate;
        private Integer adAccountId;
        private Integer cashId;
        private Integer companyId;
        private Integer ownerCompanyId;
        private Integer saleAmount;
    }
}
