package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQuerydslRepository {
    Page<UserDto.Response.Detail> search(Pageable pageable);
}
