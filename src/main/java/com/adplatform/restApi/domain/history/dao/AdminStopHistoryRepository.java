package com.adplatform.restApi.domain.history.dao;

import com.adplatform.restApi.domain.history.domain.AdminStopHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminStopHistoryRepository extends JpaRepository<AdminStopHistory, Integer>  {
}
