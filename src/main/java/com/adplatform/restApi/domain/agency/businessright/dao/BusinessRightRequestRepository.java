package com.adplatform.restApi.domain.agency.businessright.dao;

import com.adplatform.restApi.domain.agency.businessright.domain.BusinessRightRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRightRequestRepository extends JpaRepository<BusinessRightRequest, Integer>, BusinessRightRequestQuerydslRepository {
}
