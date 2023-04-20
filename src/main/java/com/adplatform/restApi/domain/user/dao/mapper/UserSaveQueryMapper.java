package com.adplatform.restApi.domain.user.dao.mapper;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserSaveQueryMapper {
    void updateUserStatus(@Param("request") UserDto.Request.UpdateStatus request, @Param("user") User user, @Param("loginUserNo") Integer loginUserNo);
    void updateUserUpdDate(@Param("userNo") Integer userNo, @Param("loginUserNo") Integer loginUserNo);
    void insertUserApproveLog(@Param("request") UserDto.Request.UpdateStatus request, @Param("user") User user, @Param("loginUserNo") Integer loginUserNo);
    void myInfoModify(@Param("request") UserDto.Request.MyInfoModify request, @Param("loginUserNo") Integer loginUserNo);

    void modify(@Param("request") UserDto.Request.Modify request, @Param("loginUserNo") Integer loginUserNo);
}
