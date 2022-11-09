package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public void save(CompanyDto.Request.Save request) {
        this.companyRepository.save(this.companyMapper.toEntity(request));
    }

    public void update(CompanyDto.Request.Update request) {
        this.companyRepository.save(CompanyFindUtils.findById(request.getId(), this.companyRepository).update(request));
    }

    public void delete(Integer id) {
        CompanyFindUtils.findById(id, this.companyRepository).delete();
    }
}
