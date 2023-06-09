package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface UserQuerydslRepository {
//    Page<UserDto.Response.Detail> search(Pageable pageable);

    Optional<User> findByLoginId(String loginId);

    UserDto.Response.BaseInfo findUserByLoginId(String loginId);

    Page<UserDto.Response.Search> userSearch(UserDto.Request.Search request, Pageable pageable);

//    List<Integer> findByUserRoles(Integer id);

    Optional<UserPasswordChangeHistory> findPasswordConfirm(AuthDto.Request.FindPasswordConfirm request);
}
