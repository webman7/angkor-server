package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.PlacementCreative;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlacementCreativeRepository extends JpaRepository<PlacementCreative, Integer>, PlacementCreativeQuerydslRepository {
//    @Modifying
//    @Query("DELETE FROM PlacementCreative up where up.id.creativeId=:creativeId")
//    void deletePlacementCreative(@Param("creativeId")  Integer creativeId);
}
