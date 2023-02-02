package com.adplatform.restApi.advertiser.adaccount.service;

import com.adplatform.restApi.advertiser.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.advertiser.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.advertiser.adaccount.exception.AdAccountUserNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountUserQueryUtils {
    public static AdAccountUser findByAdAccountIdAndUserIdOrElseThrow(Integer adAccountId, Integer userId, AdAccountUserRepository repository) {
        return repository.findByAdAccountIdAndUserId(adAccountId, userId)
                .orElseThrow(AdAccountUserNotFoundException::new);
    }
}
