package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeFileDto;
import com.adplatform.restApi.domain.bank.domain.Bank;
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
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            @NotNull
            @Email
            private String taxBillEmail;
            private String type;
            private List<MultipartFile> businessFiles = new ArrayList<>();
            private List<MultipartFile> bankFiles = new ArrayList<>();
            private Integer bankId;
            private String accountNumber;
            private String accountOwner;

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
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            private String businessCategory;
            private String businessItem;
            private String taxBillEmail;
            private Bank bank;
            private String accountNumber;
            private String accountOwner;
//            private List<CompanyFileDto> files;

            @QueryProjection
            public Detail(Integer id,
                               String name,
                               Company.Type type,
                               String registrationNumber,
                               String representationName,
                               String baseAddress,
                               String detailAddress,
                               String zipCode,
                               String businessCategory,
                               String businessItem,
                               String taxBillEmail,
                               Bank bank,
                               String accountNumber,
                               String accountOwner
//                               List<CompanyFileDto> files
                               ) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.baseAddress = baseAddress;
                this.detailAddress = detailAddress;
                this.zipCode = zipCode;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
                this.bank = bank;
                this.accountNumber = accountNumber;
                this.accountOwner = accountOwner;
//                this.files = files;
            }
        }

        @Getter
        @Setter
        public static class CompanyInfo {
            private Integer id;
            private String name;
            private Company.Type type;
            private String registrationNumber;
            private String representationName;
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            private String businessCategory;
            private String businessItem;
            private com.adplatform.restApi.global.value.Email taxBillEmail;
            private Bank bank;
            private String accountNumber;
            private String accountOwner;

            @QueryProjection
            public CompanyInfo(Integer id,
                                   String name,
                                   Company.Type type,
                                   String registrationNumber,
                                   String representationName,
                                   String baseAddress,
                                   String detailAddress,
                                   String zipCode,
                                   String businessCategory,
                                   String businessItem,
                                   com.adplatform.restApi.global.value.Email taxBillEmail,
                               Bank bank,
                               String accountNumber,
                               String accountOwner) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.baseAddress = baseAddress;
                this.detailAddress = detailAddress;
                this.zipCode = zipCode;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
                this.bank = bank;
                this.accountNumber = accountNumber;
                this.accountOwner = accountOwner;
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
            private String baseAddress;
            private String detailAddress;
            private String zipCode;
            private String businessCategory;
            private String businessItem;
            private com.adplatform.restApi.global.value.Email taxBillEmail;

            @QueryProjection
            public AdAccountDetail(Integer id,
                                   String name,
                                   Company.Type type,
                                   String registrationNumber,
                                   String representationName,
                                   String baseAddress,
                                   String detailAddress,
                                   String zipCode,
                                   String businessCategory,
                                   String businessItem,
                                   com.adplatform.restApi.global.value.Email taxBillEmail) {
                this.id = id;
                this.name = name;
                this.type = type;
                this.registrationNumber = registrationNumber;
                this.representationName = representationName;
                this.baseAddress = baseAddress;
                this.detailAddress = detailAddress;
                this.zipCode = zipCode;
                this.businessCategory = businessCategory;
                this.businessItem = businessItem;
                this.taxBillEmail = taxBillEmail;
            }
        }
    }
}
