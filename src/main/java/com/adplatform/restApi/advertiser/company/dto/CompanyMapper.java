package com.adplatform.restApi.advertiser.company.dto;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.global.value.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public interface CompanyMapper {
    @Mapping(target = "type", expression = "java(Company.Type.ADVERTISER)")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail1", qualifiedByName = "createEmail")
    @Mapping(target = "taxBillEmail2", qualifiedByName = "createEmail")
    @Mapping(target = "active", expression = "java(true)")
    Company toAdvertiserEntity(CompanyDto.Request.Save saveDto);

    @Mapping(target = "type", expression = "java(Company.Type.AGENCY)")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail1", qualifiedByName = "createEmail")
    @Mapping(target = "taxBillEmail2", qualifiedByName = "createEmail")
    @Mapping(target = "active", expression = "java(true)")
    Company toAgencyEntity(CompanyDto.Request.Save request);

    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail1", qualifiedByName = "getEmail")
    @Mapping(target = "taxBillEmail2", qualifiedByName = "getEmail")
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
