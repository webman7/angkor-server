package com.adplatform.restApi.agency.marketers.service;

import com.adplatform.restApi.advertiser.history.dao.user.UserRolesChangeHistoryRepository;
import com.adplatform.restApi.advertiser.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.advertiser.history.domain.UserRolesChangeHistory;
import com.adplatform.restApi.advertiser.history.dto.campaign.CampaignBudgetChangeHistoryDto;
import com.adplatform.restApi.advertiser.history.dto.campaign.UserRolesChangeHistoryDto;
import com.adplatform.restApi.advertiser.history.dto.campaign.UserRolesChangeHistoryMapper;
import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.dto.user.UserDto;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import com.adplatform.restApi.agency.marketers.dao.mapper.AgencyMarketersQueryMapper;
import com.adplatform.restApi.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AgencyMarketersService {

    private final UserQueryMapper userQueryMapper;
    private final UserRepository userRepository;
    private final AgencyMarketersQueryMapper agencyMarketersQueryMapper;
    private final UserRolesChangeHistoryMapper userRolesChangeHistoryMapper;
    private final UserRolesChangeHistoryRepository userRolesChangeHistoryRepository;
    public void updateUserStatus(UserDto.Request.UpdateStatus request, Integer loginUserNo) {
        User user = UserFindUtils.findByIdOrElseThrow(request.getId(), this.userRepository);

        this.userQueryMapper.updateUserStatus(request, user, loginUserNo);
        this.userQueryMapper.insertUserApproveLog(request, user, loginUserNo);
    }


    public void updateUserRoles(AgencyMarketersDto.Request.UpdateAgencyRoles request, Integer userNo, Integer loginUserNo) {

        AgencyMarketersDto.Response.SearchMarketers userMarketers = this.agencyMarketersQueryMapper.getUserMarketers(userNo);

        UserRolesChangeHistoryDto.Request.Save history = new UserRolesChangeHistoryDto.Request.Save();
        history.setPrevRoles(userMarketers.getRoles());
        history.setUserNo(userNo);
        String roleString = request.getAgencyRoles().toString().replace("[", "").replace("]", "");
        history.setRoles(roleString);

        UserRolesChangeHistory userRolesChangeHistory = this.userRolesChangeHistoryMapper.toEntity(history);
        this.userRolesChangeHistoryRepository.save(userRolesChangeHistory);

        this.userQueryMapper.deleteUserRoles(request, userNo, loginUserNo);
        this.userQueryMapper.updateUserRoles(request, userNo, loginUserNo);
    }
}
