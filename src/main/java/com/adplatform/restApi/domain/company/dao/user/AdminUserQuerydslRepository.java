package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.domain.AdminUser;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.user.AdminUserDto;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminUserQuerydslRepository {

    Optional<AdminUser> findByCompanyIdAndUserId(Integer companyId, Integer userId);
    Integer findByCompanyIdAndUserIdCount(Integer companyId, Integer userId);
    Integer findByCompanyIdCount(Integer companyId);
    void deleteByCompanyIdAndUserIdCount(Integer companyId, Integer userId);

    AdminUserDto.Response.AdminUserInfo adminUserInfo(Integer companyId, Integer userNo);

    AdminUserDto.Response.AdminUserInfo adminUserInfo(Integer userNo);

    Page<AdminUserDto.Response.AdminUserInfo> adminUserSearch(Pageable pageable, Integer companyId, AdminUserDto.Request.SearchAdminUser searchRequest);
}
