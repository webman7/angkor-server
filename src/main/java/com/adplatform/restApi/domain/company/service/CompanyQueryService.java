package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyQueryService {
    private final CompanyRepository companyRepository;

    public Company findById(Integer id) {
        return this.companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
