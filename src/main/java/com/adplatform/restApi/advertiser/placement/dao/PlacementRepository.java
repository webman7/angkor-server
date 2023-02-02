package com.adplatform.restApi.advertiser.placement.dao;

import com.adplatform.restApi.advertiser.placement.domain.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository  extends JpaRepository<Placement, Integer>, PlacementQuerydslRepository {
}
