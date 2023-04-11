package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.user.AdminUserRepository;
import com.adplatform.restApi.domain.company.domain.AdminUser;
import com.adplatform.restApi.domain.company.exception.AdminUserNotFoundException;

public class AdminUserQueryUtils {
    public static AdminUser findByCompanyIdAndUserIdOrElseThrow(Integer companyId, Integer userId, AdminUserRepository repository) {
        return repository.findByCompanyIdAndUserId(companyId, userId)
                .orElseThrow(AdminUserNotFoundException::new);
    }
}
