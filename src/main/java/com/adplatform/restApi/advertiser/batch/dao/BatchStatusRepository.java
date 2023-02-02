package com.adplatform.restApi.advertiser.batch.dao;

import com.adplatform.restApi.advertiser.batch.domain.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchStatusRepository extends JpaRepository<BatchStatus, Integer> {
}
