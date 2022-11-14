package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserNotFoundException;

public class AdAccountUserQueryUtils {
    public static AdAccountUser findByAdAccountIdAndUserIdOrElseThrow(Integer adAccountId, Integer userId, AdAccountUserRepository repository) {
        return repository.findByAdAccountIdAndUserId(adAccountId, userId)
                .orElseThrow(AdAccountUserNotFoundException::new);
    }
}
