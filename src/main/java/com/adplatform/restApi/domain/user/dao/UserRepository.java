package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserQuerydslRepository {
    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String LoginId);

    Optional<User> findByLoginIdAndName(String loginId, String name);
}
