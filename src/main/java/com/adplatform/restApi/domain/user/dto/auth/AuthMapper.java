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
 * @author junny
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public abstract class AuthMapper {
    @Mapping(target = "loginId", source = "signUpDto.id")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpDto.getPassword1()))")
    @Mapping(target = "name", source = "signUpDto.name")
    @Mapping(target = "active", expression = "java(User.Active.Y)")
    public abstract User toEntity(AuthDto.Request.SignUp signUpDto, PasswordEncoder passwordEncoder);

}
