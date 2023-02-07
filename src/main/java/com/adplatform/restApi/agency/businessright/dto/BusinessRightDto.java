package com.adplatform.restApi.agency.businessright.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BusinessRightDto {

    public static abstract class Request {
        @Getter
        @Setter
        public static class SaveRequest {
            @NotNull
            private Integer adAccountId;
            @NotNull
            private Integer companyId;
            private Integer requestCompanyId;
        }

        @Getter
        @Setter
        public static class SaveStatus {
            @NotNull
            private Integer id;
            @NotBlank
            private String status;
        }

        @Getter
        @Setter
        public static class Save {
            @NotNull
            private Integer adAccountId;
            @NotBlank
            private Integer companyId;
            private Integer startDate;
            private Integer endDate;
            private Integer requestUserNo;
        }

        @Getter
        @Setter
        public static class Statistics {
            private String platformType;
        }

        @Getter
        @Setter
        public static class Search {
            private String platformType;
            private String searchType;
            private String searchKeyword;
            private String businessRightStatus;
            private Integer companyId;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Statistics {
            private String businessRightStatus;
            private Integer count;
        }

        @Getter
        @Setter
        public static class Search {
            private Integer id;
            private String name;
            private String marketerId;
            private String marketerName;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;

            @QueryProjection
            public Search(Integer id, String name, String marketerId, String marketerName, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.id = id;
                this.name = name;
                this.marketerId = marketerId;
                this.marketerName = marketerName;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
            }
        }

        @Getter
        @Setter
        public static class BusinessRightDetail {
            private Integer id;
            private Integer adAccountId;
            private Integer companyId;
            private Integer startDate;
            private Integer endDate;

            @QueryProjection
            public BusinessRightDetail(Integer id, Integer adAccountId, Integer companyId, Integer startDate, Integer endDate) {
                this.id = id;
                this.adAccountId = adAccountId;
                this.companyId = companyId;
                this.startDate = startDate;
                this.endDate = endDate;
            }
        }
    }
}
