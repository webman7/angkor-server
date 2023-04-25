package com.adplatform.restApi.batch.dto;

import lombok.Getter;
import lombok.Setter;

public class BatchStatusDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Save {
            private String type;
            private int exeDate;
            private String name;
            private boolean exeYn;
        }

        @Getter
        @Setter
        public static class Search {
            private String type;
            private int exeDate;
            private String name;
            private boolean exeYn;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class ReportAdGroupCost {
            private Integer adAccountId;
            private Integer companyId;
            private Integer ownerCompanyId;
            private Integer reportDate;
            private Float cost;
        }

        @Getter
        @Setter
        public static class CampaignSettlementDaily {
            private Integer businessAccountId;
            private Integer adAccountId;
            private Integer campaignId;
            private Float supplyAmount;
        }

        @Getter
        @Setter
        public static class CampaignFinish {
            private Integer businessAccountId;
            private Integer adAccountId;
            private Integer campaignId;
            private String campaignName;
        }

        @Getter
        @Setter
        public static class BusinessAccountSettlement {
            private Integer businessAccountId;
            private Float supplyAmount;
        }

        @Getter
        @Setter
        public static class Batch {
            private String type;
            private int exeDate;
            private String name;
            private boolean exeYn;
            private String cnt;
        }

        @Getter
        @Setter
        public static class TaxNo {
            private String maxTaxNo;
        }

        @Getter
        @Setter
        public static class WalletCashTotal {
            private Integer adAccountId;
            private Integer cashId;
            private Integer amount;
            private Integer availableAmount;
            private Integer reserveAmount;
            private String saleAffectYn;
            private String refundYn;
        }

    }
}
