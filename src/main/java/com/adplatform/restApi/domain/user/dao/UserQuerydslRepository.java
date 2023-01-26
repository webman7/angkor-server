package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface UserQuerydslRepository {
//    Page<UserDto.Response.Detail> search(Pageable pageable);

    Optional<User> findByLoginId(String loginId);

    List<Integer> findByUserRoles(Integer id);
}
