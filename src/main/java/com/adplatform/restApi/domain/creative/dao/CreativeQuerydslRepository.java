package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeQuerydslRepository {
    Optional<Creative> findDetailById(Integer id);
}
