package com.adplatform.restApi.advertiser.creative.dao;

import com.adplatform.restApi.advertiser.creative.domain.CreativePlacement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativePlacementRepository extends JpaRepository<CreativePlacement, Integer>, CreativePlacementQuerydslRepository {
//    @Modifying
//    @Query("DELETE FROM CreativePlacement up where up.id.creativeId=:creativeId")
//    void deleteCreativePlacement(@Param("creativeId")  Integer creativeId);
}
