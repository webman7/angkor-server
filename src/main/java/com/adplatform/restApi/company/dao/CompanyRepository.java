package com.adplatform.restApi.company.dao;

import com.adplatform.restApi.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyQuerydslRepository {
}
