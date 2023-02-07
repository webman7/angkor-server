package com.adplatform.restApi.domain.agency.businessright.dao;

import com.adplatform.restApi.domain.agency.businessright.domain.BusinessRight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRightRepository extends JpaRepository<BusinessRight, Integer>, BusinessRightQuerydslRepository  {
}
