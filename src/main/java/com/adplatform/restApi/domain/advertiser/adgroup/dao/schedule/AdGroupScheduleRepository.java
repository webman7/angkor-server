package com.adplatform.restApi.domain.advertiser.adgroup.dao.schedule;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroupSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdGroupScheduleRepository extends JpaRepository<AdGroupSchedule, Integer> {
}
