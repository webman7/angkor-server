package com.adplatform.restApi.domain.statistics.dto;

import lombok.Getter;
import lombok.Setter;

public class TaxBillDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private Integer statDate;
            private Integer adAccountId;
            private Integer ownerCompanyId;
            private Integer companyId;
            private String paymentType;
            private String receiptType;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;
        }
    }

    public static class Response {
        @Getter
        @Setter
        public static class TaxBillMonthly {
            private Integer id;
            private Integer statDate;
            private Integer adAccountId;
            private Integer ownerCompanyId;
            private Integer companyId;
            private String paymentType;
            private String receiptType;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;
            private boolean issueStatus;
        }
    }
}
