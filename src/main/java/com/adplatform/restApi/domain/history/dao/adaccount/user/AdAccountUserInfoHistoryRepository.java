package com.adplatform.restApi.domain.history.dao.adaccount.user;

import com.adplatform.restApi.domain.history.domain.adaccount.user.AdAccountUserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdAccountUserInfoHistoryRepository extends JpaRepository<AdAccountUserInfoHistory, Integer> {
}

