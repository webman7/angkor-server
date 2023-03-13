package com.adplatform.restApi.domain.history.dao.business.user;

import com.adplatform.restApi.domain.history.domain.business.user.BusinessAccountUserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAccountUserInfoHistoryRepository extends JpaRepository<BusinessAccountUserInfoHistory, Integer> {
}
