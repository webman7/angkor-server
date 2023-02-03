package com.adplatform.restApi.agency.businessright.service;

import com.adplatform.restApi.advertiser.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.agency.businessright.dao.BusinessRightRequestRepository;
import com.adplatform.restApi.agency.businessright.domain.BusinessRightRequest;

public class BusinessRightRequestFindUtils {
    public static BusinessRightRequest findByIdOrElseThrow(Integer id, BusinessRightRequestRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
