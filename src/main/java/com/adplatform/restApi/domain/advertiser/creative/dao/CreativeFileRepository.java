package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author junny
 * @since 1.0
 */
public interface CreativeFileRepository extends JpaRepository<CreativeFile, Integer> {
}
