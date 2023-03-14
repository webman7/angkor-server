package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dao.user.MediaCompanyUserRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserMapper;
import com.adplatform.restApi.domain.company.exception.*;
import com.adplatform.restApi.domain.history.dao.mediaCompany.user.MediaCompanyUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.domain.mediaCompany.user.MediaCompanyUserInfoHistory;
import com.adplatform.restApi.domain.history.dto.mediaCompany.user.MediaCompanyUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.mediaCompany.user.MediaCompanyUserInfoHistoryMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.exception.UserNotFoundException;
import com.adplatform.restApi.domain.user.service.UserQueryService;
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
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserQueryService userQueryService;
    private final MediaCompanyUserRepository mediaCompanyUserRepository;
    private final MediaCompanyUserMapper mediaCompanyUserMapper;
    private final MediaCompanyUserInfoHistoryMapper mediaCompanyUserInfoHistoryMapper;
    private final MediaCompanyUserInfoHistoryRepository mediaCompanyUserInfoHistoryRepository;

    public Company findByIdOrElseThrow(Integer id) {
        return this.companyRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void saveMedia(CompanyDto.Request.Save request) {
        this.companyRepository.save(this.companyMapper.toMediaEntity(request));
    }

    public void update(CompanyDto.Request.Update request) {
        this.companyRepository.save(CompanyFindUtils.findByIdOrElseThrow(request.getId(), this.companyRepository).update(request));
    }

    public void delete(Integer id) {
        CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository).delete();
    }

    public void saveUser(MediaCompanyUserDto.Request.SaveUser request, Integer loginUserNo) {

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        Integer count = this.mediaCompanyUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        MediaCompanyUser mediaCompanyUser = this.mediaCompanyUserMapper.toEntityInvite(request, company, user);
        this.mediaCompanyUserRepository.save(mediaCompanyUser);
    }

    public void saveUserInvite(MediaCompanyUserDto.Request.SaveUser request, Integer loginUserNo) {

        // MASTER 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER)  {
            throw new MediaCompanyUserAuthorizationException();
        }

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        Integer count = this.mediaCompanyUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        MediaCompanyUser mediaCompanyUser = this.mediaCompanyUserMapper.toEntityInvite(request, company, user);
        this.mediaCompanyUserRepository.save(mediaCompanyUser);
    }

    public void updateUserMember(MediaCompanyUserDto.Request.UserMemberUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER) {
            throw new MediaCompanyUserAuthorizationException();
        }

        MediaCompanyUser mediaCompanyUser = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.mediaCompanyUserRepository);

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history.setCompanyId(mediaCompanyUser.getCompany().getId());
        history.setUserNo(mediaCompanyUser.getUser().getId());
        history.setMemberType(mediaCompanyUser.getMemberType());
        history.setAccountingYN(mediaCompanyUser.getAccountingYN());
        history.setStatus(mediaCompanyUser.getStatus());
        history.setRegUserNo(mediaCompanyUser.getCreatedUserNo());
        history.setCreatedAt(mediaCompanyUser.getCreatedAt());
        history.setUpdUserNo(mediaCompanyUser.getUpdatedUserNo());
        history.setUpdatedAt(mediaCompanyUser.getUpdatedAt());
        MediaCompanyUserInfoHistory mediaCompanyUserInfoHistory = this.mediaCompanyUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.mediaCompanyUserInfoHistoryRepository.save(mediaCompanyUserInfoHistory);

        // 비즈니스 회원 상태 업데이트
        if(request.getMemberType() == MediaCompanyUser.MemberType.MASTER) {
            mediaCompanyUser.changeMemberTypeMaster();
        } else if(request.getMemberType() == MediaCompanyUser.MemberType.OPERATOR) {
            mediaCompanyUser.changeMemberTypeOperator();
        } else if(request.getMemberType() == MediaCompanyUser.MemberType.MEMBER) {
            mediaCompanyUser.changeMemberTypeMember();
        }
    }

    public void updateUserStatus(MediaCompanyUserDto.Request.UserStatusUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER) {
            throw new MediaCompanyUserAuthorizationException();
        }

        MediaCompanyUser mediaCompanyUser = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.mediaCompanyUserRepository);

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history.setCompanyId(mediaCompanyUser.getCompany().getId());
        history.setUserNo(mediaCompanyUser.getUser().getId());
        history.setMemberType(mediaCompanyUser.getMemberType());
        history.setAccountingYN(mediaCompanyUser.getAccountingYN());
        history.setStatus(mediaCompanyUser.getStatus());
        history.setRegUserNo(mediaCompanyUser.getCreatedUserNo());
        history.setCreatedAt(mediaCompanyUser.getCreatedAt());
        history.setUpdUserNo(mediaCompanyUser.getUpdatedUserNo());
        history.setUpdatedAt(mediaCompanyUser.getUpdatedAt());
        MediaCompanyUserInfoHistory mediaCompanyUserInfoHistory = this.mediaCompanyUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.mediaCompanyUserInfoHistoryRepository.save(mediaCompanyUserInfoHistory);

        // 비즈니스 회원 상태 업데이트
        if(request.getStatus() == MediaCompanyUser.Status.Y) {
            mediaCompanyUser.changeStatusY();
        } else if(request.getStatus() == MediaCompanyUser.Status.R) {
            mediaCompanyUser.changeStatusR();
        } else if(request.getStatus() == MediaCompanyUser.Status.C) {
            mediaCompanyUser.changeStatusC();
        }
    }

    public void updateUserAccounting(MediaCompanyUserDto.Request.UserUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER) {
            throw new MediaCompanyUserAuthorizationException();
        }
        MediaCompanyUser businessAccountUser2 = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.mediaCompanyUserRepository);
        if(businessAccountUser2.getMemberType() != MediaCompanyUser.MemberType.MASTER) {
            throw new MediaCompanyUserMasterException();
        }

        // 등록자가 회계권한이 있는지 체크
        MediaCompanyUser mediaCompanyUser = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), loginUserNo, this.mediaCompanyUserRepository);

        if(mediaCompanyUser.getAccountingYN() != MediaCompanyUser.AccountingYN.Y) {
            throw new MediaCompanyUserAccountingExistException();
        }

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history.setCompanyId(mediaCompanyUser.getCompany().getId());
        history.setUserNo(loginUserNo);
        history.setMemberType(mediaCompanyUser.getMemberType());
        history.setAccountingYN(MediaCompanyUser.AccountingYN.N);
        history.setStatus(mediaCompanyUser.getStatus());
        history.setRegUserNo(mediaCompanyUser.getCreatedUserNo());
        history.setCreatedAt(mediaCompanyUser.getCreatedAt());
        history.setUpdUserNo(mediaCompanyUser.getUpdatedUserNo());
        history.setUpdatedAt(mediaCompanyUser.getUpdatedAt());
        MediaCompanyUserInfoHistory mediaCompanyUserInfoHistory = this.mediaCompanyUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.mediaCompanyUserInfoHistoryRepository.save(mediaCompanyUserInfoHistory);

        // 권한 변경
        this.mediaCompanyUserRepository.updateAccounting(mediaCompanyUser.getCompany().getId(), loginUserNo, MediaCompanyUser.AccountingYN.N);

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history2 = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history2.setCompanyId(businessAccountUser2.getCompany().getId());
        history2.setUserNo(request.getId());
        history2.setMemberType(businessAccountUser2.getMemberType());
        history2.setAccountingYN(MediaCompanyUser.AccountingYN.Y);
        history2.setStatus(businessAccountUser2.getStatus());
        history2.setRegUserNo(businessAccountUser2.getCreatedUserNo());
        history2.setCreatedAt(businessAccountUser2.getCreatedAt());
        history2.setUpdUserNo(businessAccountUser2.getUpdatedUserNo());
        history2.setUpdatedAt(businessAccountUser2.getUpdatedAt());
        MediaCompanyUserInfoHistory mediaCompanyUserInfoHistory2 = this.mediaCompanyUserInfoHistoryMapper.toEntity(history2, SecurityUtils.getLoginUserNo());
        this.mediaCompanyUserInfoHistoryRepository.save(mediaCompanyUserInfoHistory2);

        // 권한 변경
        this.mediaCompanyUserRepository.updateAccounting(businessAccountUser2.getCompany().getId(), request.getId(), MediaCompanyUser.AccountingYN.Y);
    }

    public void deleteUser(MediaCompanyUserDto.Request.UserUpdate request, Integer loginUserNo) {
        // 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        // 본인 여부 체크
        if(!mediaCompanyUserInfo.getUser().getId().equals(request.getId())) {
            // MASTER 권한 체크
            if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER) {
                throw new MediaCompanyUserAuthorizationException();
            }
        }

        MediaCompanyUser mediaCompanyUser = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.mediaCompanyUserRepository);

        if(mediaCompanyUser.getAccountingYN() == MediaCompanyUser.AccountingYN.Y) {
            throw new MediaCompanyUserAccountingException();
        }

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history.setCompanyId(mediaCompanyUser.getCompany().getId());
        history.setUserNo(mediaCompanyUser.getUser().getId());
        history.setMemberType(mediaCompanyUser.getMemberType());
        history.setAccountingYN(mediaCompanyUser.getAccountingYN());
        history.setStatus(MediaCompanyUser.Status.D);
        history.setRegUserNo(mediaCompanyUser.getCreatedUserNo());
        history.setCreatedAt(mediaCompanyUser.getCreatedAt());
        history.setUpdUserNo(mediaCompanyUser.getUpdatedUserNo());
        history.setUpdatedAt(mediaCompanyUser.getUpdatedAt());
        MediaCompanyUserInfoHistory mediaCompanyUserInfoHistory = this.mediaCompanyUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.mediaCompanyUserInfoHistoryRepository.save(mediaCompanyUserInfoHistory);

        // 삭제
        this.mediaCompanyUserRepository.deleteByCompanyIdAndUserIdCount(mediaCompanyUser.getCompany().getId(), mediaCompanyUser.getUser().getId());
    }


//    public void saveAdvertiser(CompanyDto.Request.Save request) {
//        this.companyRepository.save(this.companyMapper.toAdvertiserEntity(request));
//    }
//
//    public void saveAgency(CompanyDto.Request.Save request) {
//        this.companyRepository.save(this.companyMapper.toAgencyEntity(request));
//    }
}
