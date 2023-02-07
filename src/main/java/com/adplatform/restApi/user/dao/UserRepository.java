package com.adplatform.restApi.user.dao;

import com.adplatform.restApi.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserQuerydslRepository {
    boolean existsByLoginId(String LoginId);

    Optional<User> findByLoginIdAndName(String loginId, String name);
}
