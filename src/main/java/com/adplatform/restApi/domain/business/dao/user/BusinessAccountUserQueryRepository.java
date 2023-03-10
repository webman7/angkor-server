package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface BusinessAccountUserQueryRepository {
    Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);
}
