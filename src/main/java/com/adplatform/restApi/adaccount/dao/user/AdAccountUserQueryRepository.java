package com.adplatform.restApi.adaccount.dao.user;

import com.adplatform.restApi.adaccount.domain.AdAccountUser;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdAccountUserQueryRepository {
    Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId);
}
