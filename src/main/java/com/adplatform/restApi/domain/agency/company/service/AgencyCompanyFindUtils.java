package com.adplatform.restApi.domain.agency.company.service;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.domain.agency.company.dao.AgencyCompanyRepository;

public class AgencyCompanyFindUtils {
    public static Company findByIdOrElseThrow(Integer id, AgencyCompanyRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
