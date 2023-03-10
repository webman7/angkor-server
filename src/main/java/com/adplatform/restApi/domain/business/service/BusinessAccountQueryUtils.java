package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.exception.BusinessAccountNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class BusinessAccountQueryUtils {
    public static BusinessAccount findByIdOrElseThrow(Integer id, BusinessAccountRepository repository) {
        return repository.findById(id).orElseThrow(BusinessAccountNotFoundException::new);
    }
}
