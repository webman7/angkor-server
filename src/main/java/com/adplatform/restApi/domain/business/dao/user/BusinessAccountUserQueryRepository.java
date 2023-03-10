package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.company.dto.CompanyDto;

import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface BusinessAccountUserQueryRepository {
    Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);

    Integer findByBusinessAccountIdAndUserIdCount(Integer businessAccountId, Integer userId);
}
