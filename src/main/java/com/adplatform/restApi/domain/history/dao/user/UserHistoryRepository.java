package com.adplatform.restApi.domain.history.dao.user;

import com.adplatform.restApi.domain.history.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
}
