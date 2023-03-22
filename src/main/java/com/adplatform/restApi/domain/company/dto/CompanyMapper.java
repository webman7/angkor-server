package com.adplatform.restApi.domain.company.dto;

import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeFileMapper;
import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.CompanyFile;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.global.value.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public abstract class CompanyMapper {
    @Mapping(target = "name", source = "request.companyName")
    @Mapping(target = "type", expression = "java(Company.Type.BUSINESS)")
    @Mapping(target = "baseAddress", source = "request.baseAddress")
    @Mapping(target = "detailAddress", source = "request.detailAddress")
    @Mapping(target = "zipCode", source = "request.zipCode")
    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "accountOwner", ignore = true)
    public abstract Company toBusinessEntity(BusinessAccountDto.Request.Save request);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "type", expression = "java(Company.Type.MEDIA)")
    @Mapping(target = "baseAddress", source = "request.baseAddress")
    @Mapping(target = "detailAddress", source = "request.detailAddress")
    @Mapping(target = "zipCode", source = "request.zipCode")
    @Mapping(target = "bank", source = "bank")
    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
    public abstract Company toMediaEntity(CompanyDto.Request.Save request, Bank bank);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "type", expression = "java(Company.Type.MEDIA)")
    @Mapping(target = "baseAddress", source = "request.baseAddress")
    @Mapping(target = "detailAddress", source = "request.detailAddress")
    @Mapping(target = "zipCode", source = "request.zipCode")
    @Mapping(target = "bank", ignore = true)
    @Mapping(target = "taxBillEmail", qualifiedByName = "createEmail")
    public abstract Company toMediaNoBankEntity(CompanyDto.Request.Save request);

    @Mapping(target = "id", source = "company.id")
    @Mapping(target = "businessFile", source = "businessFile")
    @Mapping(target = "bankFile", source = "bankFile")
    @Mapping(target = "type", source = "company.type")
    @Mapping(target = "taxBillEmail", qualifiedByName = "getEmail")
    public abstract CompanyDto.Response.MediaDetail toMediaDetailResponse(Company company, CompanyFileDto.Response.Default businessFile, CompanyFileDto.Response.Default bankFile);








//    @Mapping(target = "taxBillEmail", qualifiedByName = "getEmail")
//    public abstract CompanyDto.Response.Detail toDetailResponse(Company company);

//    @Mapping(target = "files", source = "companyFile")
//    @Mapping(target = "type", source = "company.type")
//    @Mapping(target = "taxBillEmail", qualifiedByName = "getEmail")
////    @Mapping(target = "files.fileSize", source = "companyFile.fileSize")
////    @Mapping(target = "files.filename", source = "companyFile.filename")
////    @Mapping(target = "files.originalFileName", source = "companyFile.originalFileName")
////    @Mapping(target = "files.url", source = "companyFile.url")
////    @Mapping(target = "files.mimeType", source = "companyFile.mimeType")
//    public abstract CompanyDto.Response.Detail toDetailResponse(Company company, List<CompanyFileDto.Response.Default> companyFile);

    @Named("getEmail")
    String getEmail(Email email) {
        if (email == null) return "";
        return email.getAddress();
    }

    @Named("createEmail")
    Email createEmail(String email) {
        return new Email(email);
    }




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


}
