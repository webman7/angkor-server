package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;

public class CompanyFindUtils {
    public static Company findById(Integer id, CompanyRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
