package com.adplatform.restApi.domain.history.dao.user;

import com.adplatform.restApi.domain.history.domain.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
}
