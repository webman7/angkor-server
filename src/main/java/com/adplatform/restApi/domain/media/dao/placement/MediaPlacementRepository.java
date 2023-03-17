package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaPlacementRepository extends JpaRepository<MediaPlacement, Integer>, MediaPlacementQuerydslRepository {
}
