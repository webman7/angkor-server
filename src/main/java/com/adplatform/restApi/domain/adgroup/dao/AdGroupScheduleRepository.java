package com.adplatform.restApi.domain.adgroup.dao;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdGroupScheduleRepository extends JpaRepository<AdGroupSchedule, Integer> {
}
