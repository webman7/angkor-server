package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.global.value.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author junny
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public interface CompanyMapper {
    @Mapping(target = "name", source = "request.companyName")
    @Mapping(target = "type", expression = "java(Company.Type.BUSINESS)")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "accountOwner", ignore = true)
    Company toBusinessEntity(BusinessAccountDto.Request.Save request);

    @Mapping(target = "type", expression = "java(Company.Type.MEDIA)")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
    Company toMediaEntity(CompanyDto.Request.Save request);


//    @Mapping(target = "type", expression = "java(Company.Type.ADVERTISER)")
//    @Mapping(target = "address", source = "address")
//    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
//    @Mapping(target = "active", expression = "java(true)")
//    Company toAdvertiserEntity(CompanyDto.Request.Save saveDto);
//
//    @Mapping(target = "type", expression = "java(Company.Type.AGENCY)")
//    @Mapping(target = "address", source = "address")
//    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
//    @Mapping(target = "active", expression = "java(true)")
//    Company toAgencyEntity(CompanyDto.Request.Save request);


    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail", qualifiedByName = "getEmail")
    CompanyDto.Response.Detail toDetailResponse(Company company);

    @Named("getEmail")
    default String getEmail(Email email) {
        if (email == null) return "";
        return email.getAddress();
    }

    @Named("createEmail")
    default Email createEmail(String email) {
        return new Email(email);
    }
}
