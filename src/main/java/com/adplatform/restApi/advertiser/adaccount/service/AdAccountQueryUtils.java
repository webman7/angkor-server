package com.adplatform.restApi.advertiser.adaccount.service;

import com.adplatform.restApi.advertiser.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.advertiser.adaccount.domain.AdAccount;
import com.adplatform.restApi.advertiser.adaccount.exception.AdAccountNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountQueryUtils {
    public static AdAccount findByIdOrElseThrow(Integer id, AdAccountRepository repository) {
        return repository.findById(id).orElseThrow(AdAccountNotFoundException::new);
    }
}
