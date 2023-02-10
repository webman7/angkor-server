package com.adplatform.restApi.domain.statistics.dto;

import lombok.Getter;
import lombok.Setter;

public class SettlementDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private Integer statDate;
            private Integer adAccountId;
            private Integer ownerCompanyId;
            private Integer companyId;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;
            private Integer commissionRate;
            private Integer commissionSupplyAmount;
            private Integer commissionVatAmount;
            private Integer commissionAmount;
        }
    }

    public static class Response {
        @Getter
        @Setter
        public static class SettlementDaily {
            private Integer statDate;
            private Integer adAccountId;
            private Integer ownerCompanyId;
            private Integer companyId;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;
        }

        @Getter
        @Setter
        public static class SettlementMonthly {
            private Integer id;
            private Integer statDate;
            private Integer adAccountId;
            private Integer ownerCompanyId;
            private Integer companyId;
            private Integer supplyAmount;
            private Integer commissionRate;
            private Integer commissionSupplyAmount;
            private Integer commissionVatAmount;
            private Integer commissionAmount;
            private boolean issueStatus;
        }

        @Getter
        @Setter
        public static class MediaSettlementDaily {
            private Integer statDate;
            private Integer adAccountId;
            private Integer mediaId;
            private Integer supplyAmount;
            private Integer vatAmount;
            private Integer totalAmount;
        }

        @Getter
        @Setter
        public static class MediaSettlementMonthly {
            private Integer id;
            private Integer statDate;
            private Integer adAccountId;
            private Integer mediaId;
            private Integer supplyAmount;
            private Integer commissionRate;
            private Integer commissionSupplyAmount;
            private Integer commissionVatAmount;
            private Integer commissionAmount;
            private boolean issueStatus;
        }
    }
}
