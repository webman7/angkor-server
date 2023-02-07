package com.adplatform.restApi.history.dao.user;

import com.adplatform.restApi.history.domain.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Integer> {
}
