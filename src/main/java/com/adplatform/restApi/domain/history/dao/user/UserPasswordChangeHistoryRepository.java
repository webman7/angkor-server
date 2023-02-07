package com.adplatform.restApi.domain.history.dao.user;

import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordChangeHistoryRepository extends JpaRepository<UserPasswordChangeHistory, Integer> {
}
