package com.adplatform.restApi.agency.businessright.dao;

import com.adplatform.restApi.agency.businessright.domain.BusinessRightRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRightRequestRepository extends JpaRepository<BusinessRightRequest, Integer>, BusinessRightRequestQuerydslRepository {
}
