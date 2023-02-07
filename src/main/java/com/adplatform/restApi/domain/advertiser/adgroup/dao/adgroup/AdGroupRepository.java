package com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdGroupRepository extends JpaRepository<AdGroup, Integer>, AdGroupQuerydslRepository {
}
