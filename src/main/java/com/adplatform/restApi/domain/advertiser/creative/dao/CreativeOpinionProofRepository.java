package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeOpinionProofRepository extends JpaRepository<CreativeOpinionProofFile, Integer> {
}
