package com.adplatform.restApi.user.dao;

import com.adplatform.restApi.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByValue(Role.Type type);
}
