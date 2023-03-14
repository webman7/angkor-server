package com.adplatform.restApi.domain.history.dao.mediaCompany.user;

import com.adplatform.restApi.domain.history.domain.mediaCompany.user.MediaCompanyUserInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaCompanyUserInfoHistoryRepository extends JpaRepository<MediaCompanyUserInfoHistory, Integer> {
}
