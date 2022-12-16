package com.adplatform.restApi.domain.placement.dao;

import com.adplatform.restApi.domain.placement.dto.placement.PlacementDto;

import java.util.List;

public interface PlacementQuerydslRepository {
//    Optional<Placement> findDetailById(Integer id);

    List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId);
}
