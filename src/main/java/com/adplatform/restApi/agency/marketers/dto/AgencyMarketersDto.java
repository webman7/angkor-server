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

        @Getter
        @Setter
        public static class SearchMarketers {
            private String searchType;
            private String searchKeyword;
            private String status;
            private Integer loginUserNo;
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

        @Getter
        @Setter
        public static class SearchMarketers {
            private Integer id;
            private String name;
            private String loginId;
            private String companyId;
            private String statusChangeLoginId;
            private String statusChangeLoginName;
            private String status;
            private LocalDateTime createdAt;
            private LocalDateTime statusChangedAt;
        }
    }
}
