package com.adplatform.restApi.domain.history.dao.user;

import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordChangeHistoryRepository extends JpaRepository<UserPasswordChangeHistory, Integer> {

    Optional<UserPasswordChangeHistory> findByIdAndStatus(int id, UserPasswordChangeHistory.Status status);
}
