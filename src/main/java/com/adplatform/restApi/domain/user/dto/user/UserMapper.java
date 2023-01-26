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
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public interface UserMapper {

//    @Mapping(target = "id", source = "user.id")
//    @Mapping(target = "name", source = "user.name")
//    @Mapping(target = "email", qualifiedByName = "getEmail")
//    @Mapping(target = "roles", source = "roles")
//    @Mapping(target = "company", source = "company")
//    @Mapping(target = "active", source = "user.active")
//    @Mapping(target = "company.users", ignore = true)
//    @Mapping(target = "company.adAccounts", ignore = true)
//    @Mapping(target = "taxBillEmail1", qualifiedByName = "createEmail")
//    @Mapping(target = "taxBillEmail2", qualifiedByName = "createEmail")
//    UserDto.Response.Detail toDetailResponse(User user, List<Integer> roles, CompanyDto.Response.Detail company);

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
