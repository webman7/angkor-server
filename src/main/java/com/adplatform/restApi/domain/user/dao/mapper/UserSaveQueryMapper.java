package com.adplatform.restApi.domain.user.dao.mapper;

import com.adplatform.restApi.domain.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSaveQueryMapper {
    void updateUserStatus(UserDto.Request.UpdateStatus request, User user, Integer loginUserNo);
    void updateUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo);
    void deleteUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo);
    void updateUserUpdDate(Integer userNo, Integer loginUserNo);
    void insertUserApproveLog(UserDto.Request.UpdateStatus request, User user, Integer loginUserNo);
    void myInfoModify(UserDto.Request.MyInfoModify request, Integer loginUserNo);
}
