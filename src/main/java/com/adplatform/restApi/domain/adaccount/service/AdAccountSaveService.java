package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountMapper;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserMapper;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserAuthorizationException;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.exception.*;
import com.adplatform.restApi.domain.business.service.BusinessAccountQueryService;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.domain.history.dao.adaccount.user.AdAccountUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.domain.adaccount.user.AdAccountUserInfoHistory;
import com.adplatform.restApi.domain.history.dto.adaccount.user.AdAccountUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.adaccount.user.AdAccountUserInfoHistoryMapper;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AdAccountSaveService {
    private final AdAccountRepository adAccountRepository;
    private final AdAccountUserRepository adAccountUserRepository;
    private final AdAccountUserInfoHistoryRepository adAccountUserInfoHistoryRepository;
    private final AdAccountMapper adAccountMapper;
    private final UserQueryService userQueryService;
    private final BusinessAccountQueryService businessAccountQueryService;
    private final AdAccountUserMapper adAccountUserMapper;
    private final AdAccountUserInfoHistoryMapper adAccountUserInfoHistoryMapper;

    public void save(AdAccountDto.Request.Save request, Integer loginUserNo) {
        User user = this.userQueryService.findByIdOrElseThrow(loginUserNo);
        BusinessAccount businessAccount = this.businessAccountQueryService.findByIdOrElseThrow(request.getBusinessAccountId());

        AdAccount adAccount = this.adAccountMapper.toEntity(request, businessAccount)
                .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.Status.Y);
        this.adAccountRepository.save(adAccount);
    }

    public void update(AdAccountDto.Request.Update request, Integer loginUserNo) {
        this.adAccountRepository.save(AdAccountFindUtils.findByIdOrElseThrow(request.getId(), this.adAccountRepository).update(request));
    }

    public void saveUserInvite(AdAccountUserDto.Request.SaveUser request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo = this.adAccountUserRepository.adAccountUserInfo(request.getAdAccountId(), loginUserNo);
        if(adAccountUserInfo.getMemberType() != AdAccountUser.MemberType.MASTER) {
            throw new AdAccountUserAuthorizationException();
        }

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        Integer count = this.adAccountUserRepository.findByAdAccountIdAndUserIdCount(request.getAdAccountId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new BusinessAccountUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(request.getAdAccountId(), this.adAccountRepository);
        AdAccountUser adAccountUser = this.adAccountUserMapper.toEntityInvite(request, adAccount, user);
        this.adAccountUserRepository.save(adAccountUser);
    }

    public void updateUserMember(AdAccountUserDto.Request.UserMemberUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo = this.adAccountUserRepository.adAccountUserInfo(request.getAdAccountId(), loginUserNo);
        if(adAccountUserInfo.getMemberType() != AdAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        AdAccountUser adAccountUser = AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(request.getAdAccountId(), request.getId(), this.adAccountUserRepository);

        // 히스토리 저장
        AdAccountUserInfoHistoryDto.Request.Save history = new AdAccountUserInfoHistoryDto.Request.Save();
        history.setAdAccountId(adAccountUser.getAdAccount().getId());
        history.setUserNo(adAccountUser.getUser().getId());
        history.setMemberType(adAccountUser.getMemberType());
        history.setStatus(adAccountUser.getStatus());
        history.setRegUserNo(adAccountUser.getCreatedUserNo());
        history.setCreatedAt(adAccountUser.getCreatedAt());
        history.setUpdUserNo(adAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(adAccountUser.getUpdatedAt());
        AdAccountUserInfoHistory adAccountUserInfoHistory = this.adAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adAccountUserInfoHistoryRepository.save(adAccountUserInfoHistory);


        // 비즈니스 회원 상태 업데이트
        if(request.getMemberType() == AdAccountUser.MemberType.MASTER) {
            adAccountUser.changeMemberTypeMaster();
        } else if(request.getMemberType() == AdAccountUser.MemberType.OPERATOR) {
            adAccountUser.changeMemberTypeOperator();
        } else if(request.getMemberType() == AdAccountUser.MemberType.MEMBER) {
            adAccountUser.changeMemberTypeMember();
        }
    }

    public void updateUserStatus(AdAccountUserDto.Request.UserStatusUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo = this.adAccountUserRepository.adAccountUserInfo(request.getAdAccountId(), loginUserNo);
        if(adAccountUserInfo.getMemberType() != AdAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        AdAccountUser adAccountUser = AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(request.getAdAccountId(), request.getId(), this.adAccountUserRepository);

        // 히스토리 저장
        AdAccountUserInfoHistoryDto.Request.Save history = new AdAccountUserInfoHistoryDto.Request.Save();
        history.setAdAccountId(adAccountUser.getAdAccount().getId());
        history.setUserNo(adAccountUser.getUser().getId());
        history.setMemberType(adAccountUser.getMemberType());
        history.setStatus(adAccountUser.getStatus());
        history.setRegUserNo(adAccountUser.getCreatedUserNo());
        history.setCreatedAt(adAccountUser.getCreatedAt());
        history.setUpdUserNo(adAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(adAccountUser.getUpdatedAt());
        AdAccountUserInfoHistory adAccountUserInfoHistory = this.adAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adAccountUserInfoHistoryRepository.save(adAccountUserInfoHistory);

        // 비즈니스 회원 상태 업데이트
        if(request.getStatus() == AdAccountUser.Status.Y) {
            adAccountUser.changeStatusY();
        } else if(request.getStatus() == AdAccountUser.Status.R) {
            adAccountUser.changeStatusR();
        } else if(request.getStatus() == AdAccountUser.Status.C) {
            adAccountUser.changeStatusC();
        }
    }

    public void deleteUser(AdAccountUserDto.Request.UserUpdate request, Integer loginUserNo) {
        // 권한 체크
        AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo = this.adAccountUserRepository.adAccountUserInfo(request.getAdAccountId(), loginUserNo);
        // 본인 여부 체크
        if(!adAccountUserInfo.getUser().getId().equals(request.getId())) {
            // MASTER 권한 체크
            if(adAccountUserInfo.getMemberType() != AdAccountUser.MemberType.MASTER) {
                throw new AdAccountUserAuthorizationException();
            }
        }

        AdAccountUser adAccountUser = AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(request.getAdAccountId(), request.getId(), this.adAccountUserRepository);

        // 히스토리 저장
        AdAccountUserInfoHistoryDto.Request.Save history = new AdAccountUserInfoHistoryDto.Request.Save();
        history.setAdAccountId(adAccountUser.getAdAccount().getId());
        history.setUserNo(adAccountUser.getUser().getId());
        history.setMemberType(adAccountUser.getMemberType());
        history.setStatus(AdAccountUser.Status.D);
        history.setRegUserNo(adAccountUser.getCreatedUserNo());
        history.setCreatedAt(adAccountUser.getCreatedAt());
        history.setUpdUserNo(adAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(adAccountUser.getUpdatedAt());
        AdAccountUserInfoHistory adAccountUserInfoHistory = this.adAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adAccountUserInfoHistoryRepository.save(adAccountUserInfoHistory);

        // 삭제
        this.adAccountUserRepository.deleteByAdAccountIdAndUserId(adAccountUser.getAdAccount().getId(), adAccountUser.getUser().getId());
    }

    public void changeConfig(Integer id, AdAccount.Config config) {
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(id, this.adAccountRepository);
        if (config == AdAccount.Config.ON) adAccount.changeConfigOn();
        else if (config == AdAccount.Config.OFF) adAccount.changeConfigOff();
    }
}
