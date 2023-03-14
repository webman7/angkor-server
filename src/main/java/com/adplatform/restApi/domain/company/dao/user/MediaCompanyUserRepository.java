package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaCompanyUserRepository extends JpaRepository<MediaCompanyUser, Integer>, MediaCompanyUserQuerydslRepository {
}
