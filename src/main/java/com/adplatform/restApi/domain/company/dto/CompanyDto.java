package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.value.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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

            private Address address;

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
        public static class Default {
            private Integer id;
            private String name;

            @QueryProjection
            public Default(Integer id, String name) {
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
            private Address address;
            private String businessCategory;
            private String businessItem;
            private String taxBillEmail1;
            private String taxBillEmail2;
            private boolean active;
            private boolean deleted;
        }

        @Getter
        @Setter
        public static class AdAccountDetail {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private Address address;
            private String businessCategory;
            private String businessItem;
            private com.adplatform.restApi.global.value.Email taxBillEmail1;
            private com.adplatform.restApi.global.value.Email taxBillEmail2;

            @QueryProjection
            public AdAccountDetail(Integer id,
                                   String name,
                                   Company.Type type,
                                   String registrationNumber,
                                   String representationName,
                                   Address address,
                                   String businessCategory,
                                   String businessItem,
                                   com.adplatform.restApi.global.value.Email taxBillEmail1,
                                   com.adplatform.restApi.global.value.Email taxBillEmail2) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.address = address;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail1 = taxBillEmail1;
                this.taxBillEmail2 = taxBillEmail2;
            }
        }
    }
}
