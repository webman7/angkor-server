package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.exception.MediaNotFoundException;

public class MediaFindUtils {
    public static Media findByIdOrElseThrow(Integer id, MediaRepository repository) {
        return repository.findById(id)
                .orElseThrow(MediaNotFoundException::new);
    }
}
