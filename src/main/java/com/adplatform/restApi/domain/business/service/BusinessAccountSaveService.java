package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountMapper;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserMapper;
import com.adplatform.restApi.domain.business.exception.*;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.exception.CompanyAlreadyExistException;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.domain.history.dao.business.user.BusinessAccountUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.domain.business.user.BusinessAccountUserInfoHistory;
import com.adplatform.restApi.domain.history.dto.business.user.BusinessAccountUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.business.user.BusinessAccountUserInfoHistoryMapper;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class BusinessAccountSaveService {
    private final BusinessAccountRepository businessAccountRepository;
    private final BusinessAccountUserRepository businessAccountUserRepository;
    private final BusinessAccountMapper businessAccountMapper;
    private final BusinessAccountUserMapper businessAccountUserMapper;
    private final BusinessAccountUserInfoHistoryRepository businessAccountUserInfoHistoryRepository;
    private final BusinessAccountUserInfoHistoryMapper businessAccountUserInfoHistoryMapper;
    private final UserQueryService userQueryService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    private final CompanyRepository companyRepository;

    public void save(BusinessAccountDto.Request.Save request, Integer loginUserNo) {

        // 회사 중복 체크
        CompanyDto.Request.SearchKeyword searchRequest = new CompanyDto.Request.SearchKeyword();
        searchRequest.setSearchKeyword(request.getRegistrationNumber());

        Integer companyCount = this.companyRepository.registrationNumberCount(searchRequest);

        if(!companyCount.equals(0)) {
            throw new CompanyAlreadyExistException();
        }

        // 회사 정보 인서트
        Company company = this.companyRepository.save(this.companyMapper.toBusinessEntity(request));
        // 인서트한 아이디를 가져온다.
        request.setCompanyId(company.getId());

        User user = this.userQueryService.findByIdOrElseThrow(loginUserNo);

        BusinessAccount businessAccount = this.businessAccountMapper.toEntity(request, company)
                .addBusinessAccountUser(user, BusinessAccountUser.MemberType.MASTER, BusinessAccountUser.AccountingYN.Y, BusinessAccountUser.Status.Y)
                .changeWalletMaster(WalletMaster.create());
        this.businessAccountRepository.save(businessAccount);

//        System.out.println("==========================================================");
//        this.walletMasterRepository.openDateUpdate(1, Integer.getInteger(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        System.out.println(WalletFindUtils.findByIdOrElseThrow(1, this.walletMasterRepository));
    }

    public void saveUserInvite(BusinessAccountUserDto.Request.SaveUser request, Integer loginUserNo) {

        // MASTER 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        Integer count = this.businessAccountUserRepository.findByBusinessAccountIdAndUserIdCount(request.getBusinessAccountId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new BusinessAccountUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(request.getBusinessAccountId(), this.businessAccountRepository);
        BusinessAccountUser businessAccountUser = this.businessAccountUserMapper.toEntityInvite(request, businessAccount, user);
        this.businessAccountUserRepository.save(businessAccountUser);
    }

    public void updateUserStatus(BusinessAccountUserDto.Request.UserStatusUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getId(), this.businessAccountUserRepository);

        // 비즈니스 회원 상태 업데이트
        if(request.getStatus() == BusinessAccountUser.Status.Y) {
            businessAccountUser.changeStatusY();
        } else if(request.getStatus() == BusinessAccountUser.Status.R) {
            businessAccountUser.changeStatusR();
        } else if(request.getStatus() == BusinessAccountUser.Status.C) {
            businessAccountUser.changeStatusC();
        }

        // 히스토리 저장
        BusinessAccountUserInfoHistoryDto.Request.Save history = new BusinessAccountUserInfoHistoryDto.Request.Save();
        history.setBusinessAccountId(businessAccountUser.getBusinessAccount().getId());
        history.setUserNo(businessAccountUser.getUser().getId());
        history.setMemberType(businessAccountUser.getMemberType());
        history.setAccountingYN(businessAccountUser.getAccountingYN());
        history.setStatus(businessAccountUser.getStatus());
        history.setRegUserNo(businessAccountUser.getCreatedUserNo());
        history.setCreatedAt(businessAccountUser.getCreatedAt());
        history.setUpdUserNo(businessAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(businessAccountUser.getUpdatedAt());
        BusinessAccountUserInfoHistory businessAccountUserInfoHistory = this.businessAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.businessAccountUserInfoHistoryRepository.save(businessAccountUserInfoHistory);
    }

    public void updateUserAccounting(BusinessAccountUserDto.Request.UserUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }
        BusinessAccountUser businessAccountUser2 = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getId(), this.businessAccountUserRepository);
        if(businessAccountUser2.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserMasterException();
        }

        // 등록자가 회계권한이 있는지 체크
        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), loginUserNo, this.businessAccountUserRepository);

        if(businessAccountUser.getAccountingYN() != BusinessAccountUser.AccountingYN.Y) {
            throw new BusinessAccountUserAccountingExistException();
        }

        // 권한 변경
        this.businessAccountUserRepository.updateAccounting(businessAccountUser.getBusinessAccount().getId(), loginUserNo, BusinessAccountUser.AccountingYN.N);

        // 히스토리 저장
        BusinessAccountUserInfoHistoryDto.Request.Save history = new BusinessAccountUserInfoHistoryDto.Request.Save();
        history.setBusinessAccountId(businessAccountUser.getBusinessAccount().getId());
        history.setUserNo(loginUserNo);
        history.setMemberType(businessAccountUser.getMemberType());
        history.setAccountingYN(BusinessAccountUser.AccountingYN.N);
        history.setStatus(businessAccountUser.getStatus());
        history.setRegUserNo(businessAccountUser.getCreatedUserNo());
        history.setCreatedAt(businessAccountUser.getCreatedAt());
        history.setUpdUserNo(businessAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(businessAccountUser.getUpdatedAt());
        BusinessAccountUserInfoHistory businessAccountUserInfoHistory = this.businessAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.businessAccountUserInfoHistoryRepository.save(businessAccountUserInfoHistory);

        // 권한 변경
        this.businessAccountUserRepository.updateAccounting(businessAccountUser2.getBusinessAccount().getId(), request.getId(), BusinessAccountUser.AccountingYN.Y);

        // 히스토리 저장
        BusinessAccountUserInfoHistoryDto.Request.Save history2 = new BusinessAccountUserInfoHistoryDto.Request.Save();
        history2.setBusinessAccountId(businessAccountUser2.getBusinessAccount().getId());
        history2.setUserNo(request.getId());
        history2.setMemberType(businessAccountUser2.getMemberType());
        history2.setAccountingYN(BusinessAccountUser.AccountingYN.Y);
        history2.setStatus(businessAccountUser2.getStatus());
        history2.setRegUserNo(businessAccountUser2.getCreatedUserNo());
        history2.setCreatedAt(businessAccountUser2.getCreatedAt());
        history2.setUpdUserNo(businessAccountUser2.getUpdatedUserNo());
        history2.setUpdatedAt(businessAccountUser2.getUpdatedAt());
        BusinessAccountUserInfoHistory businessAccountUserInfoHistory2 = this.businessAccountUserInfoHistoryMapper.toEntity(history2, SecurityUtils.getLoginUserNo());
        this.businessAccountUserInfoHistoryRepository.save(businessAccountUserInfoHistory2);
    }

    public void deleteUser(BusinessAccountUserDto.Request.UserUpdate request, Integer loginUserNo) {
        // 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        // 본인 여부 체크
        if(!businessAccountUserInfo.getUser().getId().equals(request.getId())) {
            // MASTER 권한 체크
            if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
                throw new BusinessAccountUserAuthorizationException();
            }
        }

        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getId(), this.businessAccountUserRepository);

        if(businessAccountUser.getAccountingYN() == BusinessAccountUser.AccountingYN.Y) {
            throw new BusinessAccountUserAccountingException();
        }

        // 히스토리 저장
        BusinessAccountUserInfoHistoryDto.Request.Save history = new BusinessAccountUserInfoHistoryDto.Request.Save();
        history.setBusinessAccountId(businessAccountUser.getBusinessAccount().getId());
        history.setUserNo(businessAccountUser.getUser().getId());
        history.setMemberType(businessAccountUser.getMemberType());
        history.setAccountingYN(businessAccountUser.getAccountingYN());
        history.setStatus(BusinessAccountUser.Status.D);
        history.setRegUserNo(businessAccountUser.getCreatedUserNo());
        history.setCreatedAt(businessAccountUser.getCreatedAt());
        history.setUpdUserNo(businessAccountUser.getUpdatedUserNo());
        history.setUpdatedAt(businessAccountUser.getUpdatedAt());
        BusinessAccountUserInfoHistory businessAccountUserInfoHistory = this.businessAccountUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.businessAccountUserInfoHistoryRepository.save(businessAccountUserInfoHistory);

        // 삭제
        this.businessAccountUserRepository.deleteByBusinessAccountIdAndUserIdCount(businessAccountUser.getBusinessAccount().getId(), businessAccountUser.getUser().getId());
    }

    public void creditLimitUpdate(BusinessAccountDto.Request.CreditLimitUpdate request) {
        BusinessAccountFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountRepository).creditLimitUpdate(request);
    }

    public void changeConfig(Integer id, BusinessAccount.Config config) {
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(id, this.businessAccountRepository);
        if (config == BusinessAccount.Config.ON) businessAccount.changeConfigOn();
        else if (config == BusinessAccount.Config.OFF) businessAccount.changeConfigOff();
    }
}
