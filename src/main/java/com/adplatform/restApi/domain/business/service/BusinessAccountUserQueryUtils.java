package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import com.adplatform.restApi.domain.business.exception.BusinessAccountUserNotFoundException;

/**
 * @author junny
 * @since 1.0
 */
public class BusinessAccountUserQueryUtils {
    public static BusinessAccountUser findByBusinessAccountIdAndUserIdOrElseThrow(Integer businessAccountId, Integer userId, BusinessAccountUserRepository repository) {
        return repository.findByBusinessAccountIdAndUserId(businessAccountId, userId)
                .orElseThrow(BusinessAccountUserNotFoundException::new);
    }
}
