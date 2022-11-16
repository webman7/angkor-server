package com.adplatform.restApi.domain.adgroup.dao.target;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupDemographicTarget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdGroupDemographicTargetRepository extends JpaRepository<AdGroupDemographicTarget, Integer> {
}
