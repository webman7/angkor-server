package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaPlacementQuerydslRepository {

    Page<MediaPlacementDto.Response.Search> search(Pageable pageable, MediaPlacementDto.Request.Search searchRequest);

    Integer findByWidthAndHeight(Integer width, Integer height);
}
