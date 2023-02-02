package com.adplatform.restApi.advertiser.creative.dao;

import com.adplatform.restApi.advertiser.creative.domain.PlacementCreative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementCreativeRepository extends JpaRepository<PlacementCreative, Integer>, PlacementCreativeQuerydslRepository {
//    @Modifying
//    @Query("DELETE FROM PlacementCreative up where up.id.creativeId=:creativeId")
//    void deletePlacementCreative(@Param("creativeId")  Integer creativeId);
}
