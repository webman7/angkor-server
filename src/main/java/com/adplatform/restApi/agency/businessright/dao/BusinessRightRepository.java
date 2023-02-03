package com.adplatform.restApi.agency.businessright.dao;

import com.adplatform.restApi.agency.businessright.domain.BusinessRight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRightRepository extends JpaRepository<BusinessRight, Integer>, BusinessRightQuerydslRepository  {
}
