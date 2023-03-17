package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.domain.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepository extends JpaRepository<Placement, Integer>, PlacementQuerydslRepository {
}
