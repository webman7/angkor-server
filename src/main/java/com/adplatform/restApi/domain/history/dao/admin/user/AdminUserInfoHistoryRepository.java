package com.adplatform.restApi.domain.history.dao.admin.user;

import com.adplatform.restApi.domain.history.domain.admin.user.AdminUserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserInfoHistoryRepository extends JpaRepository<AdminUserInfoHistory, Integer> {
}
