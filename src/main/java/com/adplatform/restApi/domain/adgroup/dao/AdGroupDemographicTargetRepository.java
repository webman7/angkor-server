package com.adplatform.restApi.domain.adgroup.dao;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupDemographicTarget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdGroupDemographicTargetRepository extends JpaRepository<AdGroupDemographicTarget, Integer> {
}
