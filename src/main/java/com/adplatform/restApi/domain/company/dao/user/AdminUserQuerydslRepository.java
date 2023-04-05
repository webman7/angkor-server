package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.dto.user.AdminUserDto;

public interface AdminUserQuerydslRepository {

    AdminUserDto.Response.AdminUserInfo adminUserInfo(Integer userNo);
}
