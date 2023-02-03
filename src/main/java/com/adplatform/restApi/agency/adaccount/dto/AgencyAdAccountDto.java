package com.adplatform.restApi.agency.adaccount.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

//            @QueryProjection
//            public Search(Integer id, String name, String adAccountType, String businessRegistrationNumber, Boolean isBusinessRight, Integer companyId, Integer ownerCompanyId, String config) {
//                this.id = id;
//                this.name = name;
//                this.adAccountType = adAccountType;
//                this.businessRegistrationNumber = businessRegistrationNumber;
//                this.isBusinessRight = isBusinessRight;
//                this.companyId = companyId;
//                this.ownerCompanyId = ownerCompanyId;
//                this.config = config;
//            }
        }
    }
}
