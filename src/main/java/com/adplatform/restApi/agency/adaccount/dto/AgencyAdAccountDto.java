package com.adplatform.restApi.agency.adaccount.dto;

import lombok.Getter;
import lombok.Setter;

public class AgencyAdAccountDto {

    public static abstract class Request {

        @Getter
        @Setter
        public static class Search {
            private String platformType;
            private String searchType;
            private String searchKeyword;
        }
    }

    public static abstract class Response {


        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private String name;
            private String adAccountType;
            private String businessRegistrationNumber;
            private Boolean isBusinessRight;
            private Integer companyId;
            private Integer ownerCompanyId;
            private String config;
        }
    }
}
