package com.adplatform.restApi.domain.placement.dao;

import com.adplatform.restApi.domain.placement.domain.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository  extends JpaRepository<Placement, Integer>, PlacementQuerydslRepository {
}
