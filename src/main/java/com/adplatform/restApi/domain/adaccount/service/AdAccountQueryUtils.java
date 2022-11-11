package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountNotFoundException;

public class AdAccountQueryUtils {
    public static AdAccount findById(Integer id, AdAccountRepository repository) {
        return repository.findById(id).orElseThrow(AdAccountNotFoundException::new);
    }
}
