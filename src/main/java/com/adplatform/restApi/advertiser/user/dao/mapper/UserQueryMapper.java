package com.adplatform.restApi.advertiser.user.dao.mapper;

import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.dto.user.UserDto;
import com.adplatform.restApi.agency.marketers.dto.AgencyMarketersDto;
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

    void updateUserStatus(UserDto.Request.UpdateStatus request, User user, Integer loginUserNo);
    void insertUserApproveLog(UserDto.Request.UpdateStatus request, User user, Integer loginUserNo);

    void updateUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo);
    void deleteUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo);
    void updateUserUpdDate(Integer userNo, Integer loginUserNo);
}
