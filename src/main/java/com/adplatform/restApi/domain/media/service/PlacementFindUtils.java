package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.media.dao.placement.PlacementRepository;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.exception.PlacementNotFoundException;

public class PlacementFindUtils {
    public static Placement findByIdOrElseThrow(Integer id, PlacementRepository repository) {
        return repository.findById(id)
                .orElseThrow(PlacementNotFoundException::new);
    }
}