package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface CreativeQuerydslRepository {
    Optional<Creative> findDetailById(Integer id);

    List<CreativeFile> findDetailFilesById(Integer id);

    List<CreativeOpinionProofFile> findDetailOpinionProofById(Integer id);
}
