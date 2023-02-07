package com.adplatform.restApi.agency.company.dao;

import com.adplatform.restApi.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyCompanyRepository extends JpaRepository<Company, Integer>, AgencyCompanyQuerydslRepository {
}
