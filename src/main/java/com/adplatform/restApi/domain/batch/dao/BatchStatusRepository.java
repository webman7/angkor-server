package com.adplatform.restApi.domain.batch.dao;

import com.adplatform.restApi.domain.batch.domain.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchStatusRepository extends JpaRepository<BatchStatus, Integer> {
}
