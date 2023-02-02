package com.adplatform.restApi.advertiser.company.service;

import com.adplatform.restApi.advertiser.company.dao.CompanyRepository;
import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.advertiser.company.dto.CompanyMapper;
import com.adplatform.restApi.advertiser.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public Company findByIdOrElseThrow(Integer id) {
        return this.companyRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void saveAdvertiser(CompanyDto.Request.Save request) {
        this.companyRepository.save(this.companyMapper.toAdvertiserEntity(request));
    }

    public void saveAgency(CompanyDto.Request.Save request) {
        this.companyRepository.save(this.companyMapper.toAgencyEntity(request));
    }

    public void update(CompanyDto.Request.Update request) {
        this.companyRepository.save(CompanyFindUtils.findByIdOrElseThrow(request.getId(), this.companyRepository).update(request));
    }

    public void delete(Integer id) {
        CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository).delete();
    }
}