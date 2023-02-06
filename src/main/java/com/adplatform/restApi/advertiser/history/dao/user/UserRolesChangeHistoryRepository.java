package com.adplatform.restApi.advertiser.history.dao.user;

import com.adplatform.restApi.advertiser.history.domain.UserRolesChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesChangeHistoryRepository extends JpaRepository<UserRolesChangeHistory, Integer> {
}
