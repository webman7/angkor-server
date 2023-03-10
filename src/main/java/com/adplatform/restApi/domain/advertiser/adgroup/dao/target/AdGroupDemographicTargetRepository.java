package com.adplatform.restApi.domain.advertiser.adgroup.dao.target;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroupDemographicTarget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author junny
 * @since 1.0
 */
public interface AdGroupDemographicTargetRepository extends JpaRepository<AdGroupDemographicTarget, Integer> {
}
