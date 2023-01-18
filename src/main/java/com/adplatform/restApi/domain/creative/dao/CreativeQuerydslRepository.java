package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.creative.domain.CreativeOpinionProofFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeQuerydslRepository {
    Optional<Creative> findDetailById(Integer id);

    List<CreativeFile> findDetailFilesById(Integer id);

    List<CreativeOpinionProofFile> findDetailOpinionProofById(Integer id);
}
