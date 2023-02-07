package com.adplatform.restApi.domain.agency.company.dao;

import com.adplatform.restApi.domain.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyCompanyRepository extends JpaRepository<Company, Integer>, AgencyCompanyQuerydslRepository {
}
