package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer>, CompanyQuerydslRepository {
}
