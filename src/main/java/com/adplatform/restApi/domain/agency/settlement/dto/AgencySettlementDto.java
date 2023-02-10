package com.adplatform.restApi.domain.agency.settlement.dto;

import lombok.Getter;
import lombok.Setter;

public class AgencySettlementDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class AmountSum {
            private String platformType;
            private Integer companyId;
            private Integer startDate;
            private Integer endDate;
        }

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
        public static class AmountSum {
            private Integer cash;
            private Integer freeCash;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private Integer commissionAmount;
            private Integer commissionRate;
            private Integer commissionSupplyAmount;
            private Integer commissionVatAmount;
            private String issueStatus;
            private Integer statDate;
            private Integer supplyAmount;
            private String paymentStatus;
        }
    }
}
