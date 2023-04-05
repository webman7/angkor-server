package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.domain.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer>, AdminUserQuerydslRepository {
}
