package com.adplatform.restApi.domain.agency.marketers.service;

import com.adplatform.restApi.domain.agency.marketers.dao.mapper.AgencyMarketersQueryMapper;
import com.adplatform.restApi.domain.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.service.UserFindUtils;
import com.adplatform.restApi.domain.user.dao.mapper.UserSaveQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AgencyMarketersService {

    private final UserSaveQueryMapper userSaveQueryMapper;
    private final UserRepository userRepository;
    private final AgencyMarketersQueryMapper agencyMarketersQueryMapper;
    public void updateUserStatus(UserDto.Request.UpdateStatus request, Integer loginUserNo) {
        User user = UserFindUtils.findByIdOrElseThrow(request.getId(), this.userRepository);

        this.userSaveQueryMapper.updateUserStatus(request, user, loginUserNo);
        this.userSaveQueryMapper.insertUserApproveLog(request, user, loginUserNo);
    }


    public void updateUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo) {

//        AgencyMarketersDto.Response.SearchMarketers userMarketers = this.agencyMarketersQueryMapper.getUserMarketers(userNo);
//
//        UserRolesChangeHistoryDto.Request.Save history = new UserRolesChangeHistoryDto.Request.Save();
//        history.setPrevRoles(userMarketers.getRoles());
//        history.setUserNo(userNo);
//        String roleString = request.getAgencyRoles().toString().replace("[", "").replace("]", "");
//        history.setRoles(roleString);
//
//        UserRolesChangeHistory userRolesChangeHistory = this.userRolesChangeHistoryMapper.toEntity(history);
//        this.userRolesChangeHistoryRepository.save(userRolesChangeHistory);
//
//        this.userSaveQueryMapper.deleteUserRoles(request, userNo, loginUserNo);
//        this.userSaveQueryMapper.updateUserRoles(request, userNo, loginUserNo);
//        this.userSaveQueryMapper.updateUserUpdDate(userNo, loginUserNo);
    }
}
