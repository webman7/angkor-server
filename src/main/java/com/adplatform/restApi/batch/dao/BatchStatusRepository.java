package com.adplatform.restApi.batch.dao;

import com.adplatform.restApi.batch.domain.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchStatusRepository extends JpaRepository<BatchStatus, Integer> {
}
