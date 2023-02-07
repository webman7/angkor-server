package com.adplatform.restApi.domain.user.dto.auth;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public abstract class AuthMapper {
    @Autowired
    protected CompanyRepository companyRepository;

    @Mapping(target = "company", source = "signUpDto.companyId", qualifiedByName = "mapCompany")
    @Mapping(target = "loginId", source = "signUpDto.id")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpDto.getPassword1()))")
    @Mapping(target = "name", source = "signUpDto.name")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "active", expression = "java(User.Active.W)")
    public abstract User toEntity(AuthDto.Request.SignUp signUpDto, PasswordEncoder passwordEncoder);

    @Named("mapCompany")
    protected Company mapCompany(Integer id) {
        return CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository);
    }
}
