package com.adplatform.restApi.domain.adgroup.dao;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdGroupRepository extends JpaRepository<AdGroup, Integer> {
}
