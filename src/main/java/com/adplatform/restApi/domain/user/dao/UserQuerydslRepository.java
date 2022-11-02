package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserQuerydslRepository {
    Page<UserDto.Response.Detail> search(Pageable pageable);

    Optional<User> findByLoginId(String loginId);
}
