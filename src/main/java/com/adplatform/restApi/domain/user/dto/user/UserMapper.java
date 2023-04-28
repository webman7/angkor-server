package com.adplatform.restApi.domain.user.dto.user;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.user.domain.User;
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
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "active", source = "user.active")
    UserDto.Response.Detail toDetailResponse(User user);

//    @Named("getEmail")
//    default String getEmail(Email email) {
//        if (email == null) return "";
//        return email.getAddress();
//    }
//
//    @Named("createEmail")
//    default Email createEmail(String email) {
//        return new Email(email);
//    }
}
