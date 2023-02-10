package com.adplatform.restApi.domain.agency.taxbill.dto;

import lombok.Getter;
import lombok.Setter;

public class AgencyTaxBillDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
            private String platformType;
            private Integer companyId;
            private Integer startDate;
            private Integer endDate;
        }
    }

    public static class Response {
        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private String issueStatus;
            private Integer statDate;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;

        }
    }
}
