package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.user.MediaCompanyUserRepository;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.exception.MediaCompanyUserNotFoundException;

public class MediaCompanyUserQueryUtils {
    public static MediaCompanyUser findByCompanyIdAndUserIdOrElseThrow(Integer companyId, Integer userId, MediaCompanyUserRepository repository) {
        return repository.findByCompanyIdAndUserId(companyId, userId)
                .orElseThrow(MediaCompanyUserNotFoundException::new);
    }
}
