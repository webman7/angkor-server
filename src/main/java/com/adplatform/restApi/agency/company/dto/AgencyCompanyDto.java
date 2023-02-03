package com.adplatform.restApi.agency.company.dto;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.global.value.Address;
import lombok.Getter;
import lombok.Setter;

public abstract class AgencyCompanyDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class SpendSummary {
            private String platformType;
            private Integer prePaymentCnt;
            private Integer deferredPaymentCnt;
            private Integer todaySpend;
            private Integer yesterdaySpend;
            private Integer monthSpend;
        }

        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private Address address;
            private String businessCategory;
            private String businessItem;
            private String taxBillEmail1;
            private String taxBillEmail2;
            private boolean active;
            private boolean deleted;
        }

    }
}
