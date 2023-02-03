package com.adplatform.restApi.agency.marketers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AgencyMarketersDto {

    public static abstract class Request {
            @Getter
            @Setter
            public static class Search {
                private String platformType;
                private Integer userNo;
            }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class MarketersDetail {
            private Integer id;
            private String loginId;
            private String name;
            private String companyName;
            private String status;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private String name;
        }
    }
}
