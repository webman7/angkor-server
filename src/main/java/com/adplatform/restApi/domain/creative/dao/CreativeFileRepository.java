package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeFileRepository extends JpaRepository<CreativeFile, Integer> {
}
