package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.CreativeOpinionProofFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativeOpinionProofRepository extends JpaRepository<CreativeOpinionProofFile, Integer> {
}
