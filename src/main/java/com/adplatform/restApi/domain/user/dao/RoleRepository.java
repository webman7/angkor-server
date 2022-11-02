package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(Role.Type type);
}
