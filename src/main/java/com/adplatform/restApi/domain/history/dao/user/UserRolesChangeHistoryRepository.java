package com.adplatform.restApi.domain.history.dao.user;

import com.adplatform.restApi.domain.history.domain.UserRolesChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesChangeHistoryRepository extends JpaRepository<UserRolesChangeHistory, Integer> {
}
