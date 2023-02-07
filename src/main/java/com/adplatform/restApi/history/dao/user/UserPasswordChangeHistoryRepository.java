package com.adplatform.restApi.history.dao.user;

import com.adplatform.restApi.history.domain.UserPasswordChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPasswordChangeHistoryRepository extends JpaRepository<UserPasswordChangeHistory, Integer> {
}
