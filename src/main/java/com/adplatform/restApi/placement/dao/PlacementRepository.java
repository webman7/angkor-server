package com.adplatform.restApi.placement.dao;

import com.adplatform.restApi.placement.domain.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository  extends JpaRepository<Placement, Integer>, PlacementQuerydslRepository {
}
