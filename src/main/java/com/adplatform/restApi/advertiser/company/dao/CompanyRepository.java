package com.adplatform.restApi.advertiser.company.dao;

import com.adplatform.restApi.advertiser.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyQuerydslRepository {
}
