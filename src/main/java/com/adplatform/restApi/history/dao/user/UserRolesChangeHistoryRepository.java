package com.adplatform.restApi.history.dao.user;

import com.adplatform.restApi.history.domain.UserRolesChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesChangeHistoryRepository extends JpaRepository<UserRolesChangeHistory, Integer> {
}
