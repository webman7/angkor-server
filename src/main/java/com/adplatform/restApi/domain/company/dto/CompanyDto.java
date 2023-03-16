package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.CompanyFile;
import com.adplatform.restApi.global.value.Address;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
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
        public static class SearchKeyword {
            private Company.Type type;
            private String searchKeyword;
        }

        @Getter
        @Setter
        public static class SearchMedia {
            private String name;
            private String registrationNumber;
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
            private String taxBillEmail;

            @NotNull
            private CompanyFile.Type type;
            @NotNull
            @Size(min = 1, max = 100)
            private List<MultipartFile> businessFiles = new ArrayList<>();
            private List<MultipartFile> bankFiles = new ArrayList<>();

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
            private String taxBillEmail;
            private boolean deleted;
        }

        @Getter
        @Setter
        public static class CompanyInfo {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private Address address;
            private String businessCategory;
            private String businessItem;
            private com.adplatform.restApi.global.value.Email taxBillEmail;

            @QueryProjection
            public CompanyInfo(Integer id,
                                   String name,
                                   Company.Type type,
                                   String registrationNumber,
                                   String representationName,
                                   Address address,
                                   String businessCategory,
                                   String businessItem,
                                   com.adplatform.restApi.global.value.Email taxBillEmail) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.address = address;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
            }
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
            private com.adplatform.restApi.global.value.Email taxBillEmail;

            @QueryProjection
            public AdAccountDetail(Integer id,
                                   String name,
                                   Company.Type type,
                                   String registrationNumber,
                                   String representationName,
                                   Address address,
                                   String businessCategory,
                                   String businessItem,
                                   com.adplatform.restApi.global.value.Email taxBillEmail) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.address = address;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
            }
        }
    }
}
