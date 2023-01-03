package com.adplatform.restApi.domain.user.dao.mapper;

import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.dto.user.UserSearchRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface UserQueryMapper {
    List<UserDto.Response.Search> search(
            @Param("request") UserDto.Request.Search request,
            @Param("pageable") Pageable pageable
    );

    long countSearch(@Param("request") UserDto.Request.Search request);
}
