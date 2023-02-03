package com.adplatform.restApi.agency.businessright.service;

import com.adplatform.restApi.advertiser.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.agency.businessright.dao.BusinessRightRepository;
import com.adplatform.restApi.agency.businessright.domain.BusinessRight;

public class BusinessRightFindUtils {
    public static BusinessRight findByIdOrElseThrow(Integer id, BusinessRightRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
