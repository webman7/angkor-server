package com.adplatform.restApi.agency.company.service;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.advertiser.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.agency.company.dao.AgencyCompanyRepository;

public class AgencyCompanyFindUtils {
    public static Company findByIdOrElseThrow(Integer id, AgencyCompanyRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
