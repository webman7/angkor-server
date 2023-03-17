package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;

import java.util.List;

public interface PlacementQuerydslRepository {
//    Optional<Placement> findDetailById(Integer id);

    List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId);
}
