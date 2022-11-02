package com.adplatform.restApi.domain.user.dto.auth;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = BaseMapperConfig.class)
public interface AuthMapper {
    @Mapping(target = "loginId", source = "signUpDto.id")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpDto.getPassword()))")
    @Mapping(target = "name", source = "signUpDto.name")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "active", expression = "java(User.Active.Y)")
    User toEntity(AuthDto.Request.SignUp signUpDto, PasswordEncoder passwordEncoder);
}
