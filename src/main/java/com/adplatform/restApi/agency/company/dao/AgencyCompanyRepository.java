package com.adplatform.restApi.agency.company.dao;

import com.adplatform.restApi.agency.businessright.domain.BusinessRight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyCompanyRepository extends JpaRepository<BusinessRight, Integer>, AgencyCompanyQuerydslRepository {
}
