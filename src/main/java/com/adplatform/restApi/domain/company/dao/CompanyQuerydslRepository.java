package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CompanyQuerydslRepository {
    Page<CompanyDto.Response.Default> search(Pageable pageable, CompanyDto.Request.Search searchRequest);

    Page<CompanyDto.Response.Default> advertiserRegistrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest);

    Page<CompanyDto.Response.Default> allRegistrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest);

    List<CompanyDto.Response.Default> searchForSignUp(Company.Type type, String name);
}
