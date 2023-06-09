package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountNotFoundException;

/**
 * @author junny
 * @since 1.0
 */
public class AdAccountQueryUtils {
    public static AdAccount findByIdOrElseThrow(Integer id, AdAccountRepository repository) {
        return repository.findById(id).orElseThrow(AdAccountNotFoundException::new);
    }
}
