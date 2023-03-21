package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.CompanyFile;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
public interface CompanyQuerydslRepository {
    Page<CompanyDto.Response.Default> search(Pageable pageable, CompanyDto.Request.Search searchRequest);

    Page<CompanyDto.Response.CompanyInfo> searchMedia(Pageable pageable, CompanyDto.Request.SearchMedia searchRequest);

//    Page<CompanyDto.Response.Default> advertiserRegistrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest);
//
//    Page<CompanyDto.Response.Default> agencyRegistrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest);

    Page<CompanyDto.Response.AdAccountDetail> registrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest);

    Integer registrationNumberCount(CompanyDto.Request.SearchKeyword searchRequest);

    Integer findByRegistrationNumberCount(String registrationNumber);

    List<CompanyFile> findDetailFilesById(Integer id);

    List<CompanyDto.Response.Default> searchForSignUp(Company.Type type, String name);
}
