package com.adplatform.restApi.placement.dao;

import com.adplatform.restApi.placement.dto.placement.PlacementDto;

import java.util.List;

public interface PlacementQuerydslRepository {
//    Optional<Placement> findDetailById(Integer id);

    List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId);
}
