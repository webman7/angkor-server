package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;

import java.util.Optional;

public interface AdAccountUserQueryRepository {
    Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId);
}
