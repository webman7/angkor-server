package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlacementQuerydslRepository {
//    Optional<Placement> findDetailById(Integer id);

    Page<PlacementDto.Response.Search> search(Pageable pageable);
    List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId);
}
