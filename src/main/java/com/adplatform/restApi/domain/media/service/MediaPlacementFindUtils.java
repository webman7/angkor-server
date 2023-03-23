package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.media.dao.placement.MediaPlacementRepository;
import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import com.adplatform.restApi.domain.media.exception.PlacementNotFoundException;

public class MediaPlacementFindUtils {
    public static MediaPlacement findByIdOrElseThrow(Integer id, MediaPlacementRepository repository) {
        return repository.findById(id)
                .orElseThrow(PlacementNotFoundException::new);
    }
}
