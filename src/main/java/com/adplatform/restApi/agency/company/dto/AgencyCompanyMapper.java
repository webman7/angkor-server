package com.adplatform.restApi.agency.company.dto;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.global.value.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public interface AgencyCompanyMapper {
    @Mapping(target = "address", source = "address")
    @Mapping(target = "taxBillEmail1", qualifiedByName = "getEmail")
    @Mapping(target = "taxBillEmail2", qualifiedByName = "getEmail")
    AgencyCompanyDto.Response.Detail toDetailResponse(Company company);

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
