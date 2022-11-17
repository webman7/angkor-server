package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class CompanyFindUtils {
    public static Company findByIdOrElseThrow(Integer id, CompanyRepository repository) {
        return repository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
