package com.adplatform.restApi.agency.adaccount.dao;

import com.adplatform.restApi.agency.businessright.domain.BusinessRight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyAdAccountRepository extends JpaRepository<BusinessRight, Integer>, AgencyAdAccountQuerydslRepository {
}
