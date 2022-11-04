package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.CreativeFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreativeFileRepository extends JpaRepository<CreativeFile, Integer> {
}
