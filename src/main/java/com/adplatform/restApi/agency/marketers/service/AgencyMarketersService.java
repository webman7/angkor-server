package com.adplatform.restApi.agency.marketers.service;

import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.dto.user.UserDto;
import com.adplatform.restApi.advertiser.user.service.UserFindUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AgencyMarketersService {

    private final UserQueryMapper userQueryMapper;
    private final UserRepository userRepository;
    public void updateUserStatus(UserDto.Request.UpdateStatus request, Integer loginUserNo) {
        User user = UserFindUtils.findByIdOrElseThrow(request.getId(), this.userRepository);

        this.userQueryMapper.updateUserStatus(request, user, loginUserNo);
        this.userQueryMapper.insertUserApproveLog(request, user, loginUserNo);
    }
}
