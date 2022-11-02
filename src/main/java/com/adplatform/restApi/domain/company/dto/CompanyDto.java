package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class CompanyDto {
    public static abstract class Request {
        @Getter
        @Setter
        public static class Search {
            private String name;

            private Company.Type type;

            private Boolean deleted;
        }

        @Getter
        @Setter
        public static class Save {
            @Size(min = 1, max = 50)
            @NotBlank
            private String name;

            @NotNull
            private Company.Type type;

            @NotBlank
            @Size(min = 1, max = 20)
            private String registrationNumber;

            @NotBlank
            @Size(min = 1, max = 50)
            private String representationName;

            @NotBlank
            @Size(min = 1, max = 20)
            private String businessCategory;

            @NotBlank
            @Size(min = 1, max = 50)
            private String businessItem;

            @NotNull
            @Email
            private String taxBillEmail1;

            @Email
            private String taxBillEmail2;
        }

        @Getter
        @Setter
        public static class Update extends Save {
            @NotNull
            private Integer id;
        }
    }

    public static abstract class Response {
        @Getter
        @Setter
        public static class Page {
            private Integer id;
            private String name;

            @QueryProjection
            public Page(Integer id, String name) {
                this.id = id;
                this.name = name;
            }
        }

        @Getter
        @Setter
        public static class Detail {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private String address;
            private String businessCategory;
            private String businessItem;
            private String taxBillEmail1;
            private String taxBillEmail2;
            private boolean active;
            private boolean deleted;
        }
    }
}
