package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.batch.dto.BatchStatusDto;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.adaccount.service.AdAccountUserQueryUtils;
import com.adplatform.restApi.domain.bank.dao.BankRepository;
import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.service.BankFindUtils;
import com.adplatform.restApi.domain.business.dao.account.BusinessAccountPreDeferredPaymentRepository;
import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.dao.account.mapper.BusinessAccountQueryMapper;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountPreDeferredPayment;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountMapper;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountPreDeferredPaymentMapper;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserMapper;
import com.adplatform.restApi.domain.business.exception.*;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.exception.CompanyAlreadyExistException;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.domain.history.dao.adaccount.user.AdAccountUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.dao.business.user.BusinessAccountUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.domain.adaccount.user.AdAccountUserInfoHistory;
import com.adplatform.restApi.domain.history.domain.business.user.BusinessAccountUserInfoHistory;
import com.adplatform.restApi.domain.history.dto.adaccount.user.AdAccountUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.adaccount.user.AdAccountUserInfoHistoryMapper;
import com.adplatform.restApi.domain.history.dto.business.user.BusinessAccountUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.business.user.BusinessAccountUserInfoHistoryMapper;
import com.adplatform.restApi.domain.statistics.dao.taxbill.BusinessAccountTaxBillRepository;
import com.adplatform.restApi.domain.statistics.domain.taxbill.*;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.domain.statistics.service.BusinessAccountTaxBillFindUtils;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
    private final BusinessAccountQueryMapper businessAccountQueryMapper;
    private final AdAccountUserRepository adAccountUserRepository;
    private final AdAccountUserInfoHistoryRepository adAccountUserInfoHistoryRepository;
    private final AdAccountUserInfoHistoryMapper adAccountUserInfoHistoryMapper;
    private final UserQueryService userQueryService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final BusinessAccountTaxBillRepository businessAccountTaxBillRepository;
    private final BankRepository bankRepository;
    private final CompanyRepository companyRepository;
    private final BusinessAccountPreDeferredPaymentMapper businessAccountPreDeferredPaymentMapper;
    private final BusinessAccountPreDeferredPaymentRepository businessAccountPreDeferredPaymentRepository;
    private final AwsFileService awsFileService;

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
        Integer businessAccountId = this.businessAccountRepository.save(businessAccount).getId();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String startDate = sdf.format(c1.getTime());

        // 선불 시작일 세팅
        this.businessAccountPreDeferredPaymentRepository.save(this.businessAccountPreDeferredPaymentMapper.toEntity(businessAccountId, Integer.parseInt(startDate)));
    }

    public void update(BusinessAccountDto.Request.Update request, Integer loginUserNo) {

        this.businessAccountRepository.save(BusinessAccountFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountRepository).update(request));
        CompanyDto.Request.Update requestCompany = new CompanyDto.Request.Update();
        requestCompany.setName(request.getCompanyName());
        requestCompany.setBaseAddress(request.getBaseAddress());
        requestCompany.setDetailAddress(request.getDetailAddress());
        requestCompany.setZipCode(request.getZipCode());
        requestCompany.setRegistrationNumber(request.getRegistrationNumber());
        requestCompany.setBusinessItem(request.getBusinessItem());
        requestCompany.setBusinessCategory(request.getBusinessCategory());
        requestCompany.setRepresentationName(request.getRepresentationName());
        requestCompany.setTaxBillEmail(request.getTaxBillEmail());
        this.companyRepository.save(CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository).update(requestCompany, null));
    }

    public void saveAdmin(BusinessAccountDto.Request.Save request, Integer loginUserNo) {

        // 회사 중복 체크
        CompanyDto.Request.SearchKeyword searchRequest = new CompanyDto.Request.SearchKeyword();
        searchRequest.setSearchKeyword(request.getRegistrationNumber());

        Integer companyCount = this.companyRepository.registrationNumberCount(searchRequest);

        if(!companyCount.equals(0)) {
            throw new CompanyAlreadyExistException();
        }

        if(request.getBankId().equals(0)) {
            // 회사 정보 인서트
            Company company = this.companyRepository.save(this.companyMapper.toBusinessEntity(request));
            // 인서트한 아이디를 가져온다.
            request.setCompanyId(company.getId());

            BusinessAccount businessAccount = this.businessAccountMapper.toEntity(request, company)
                    .changeWalletMaster(WalletMaster.create());
            Integer businessAccountId = this.businessAccountRepository.save(businessAccount).getId();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String startDate = sdf.format(c1.getTime());

            // 선불 시작일 세팅
            this.businessAccountPreDeferredPaymentRepository.save(this.businessAccountPreDeferredPaymentMapper.toEntity(businessAccountId, Integer.parseInt(startDate)));
        } else {
            Bank bank = BankFindUtils.findByIdOrElseThrow(request.getBankId(), this.bankRepository);

            // 회사 정보 인서트
            Company company = this.companyRepository.save(this.companyMapper.toBusinessAdminEntity(request, bank));
            // 인서트한 아이디를 가져온다.
            request.setCompanyId(company.getId());

            BusinessAccount businessAccount = this.businessAccountMapper.toEntity(request, company)
                    .changeWalletMaster(WalletMaster.create());
            Integer businessAccountId = this.businessAccountRepository.save(businessAccount).getId();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String startDate = sdf.format(c1.getTime());

            // 선불 시작일 세팅
            this.businessAccountPreDeferredPaymentRepository.save(this.businessAccountPreDeferredPaymentMapper.toEntity(businessAccountId, Integer.parseInt(startDate)));
        }
    }

    public void updateAdmin(BusinessAccountDto.Request.Update request, Integer loginUserNo) {

        this.businessAccountRepository.save(BusinessAccountFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountRepository).update(request));
        CompanyDto.Request.Update requestCompany = new CompanyDto.Request.Update();
        requestCompany.setName(request.getCompanyName());
        requestCompany.setBaseAddress(request.getBaseAddress());
        requestCompany.setDetailAddress(request.getDetailAddress());
        requestCompany.setZipCode(request.getZipCode());
        requestCompany.setRegistrationNumber(request.getRegistrationNumber());
        requestCompany.setBusinessItem(request.getBusinessItem());
        requestCompany.setBusinessCategory(request.getBusinessCategory());
        requestCompany.setRepresentationName(request.getRepresentationName());
        requestCompany.setTaxBillEmail(request.getTaxBillEmail());
        requestCompany.setAccountNumber(request.getAccountNumber());
        requestCompany.setAccountOwner(request.getAccountOwner());
        Bank bank = null;
        if(!request.getBankId().equals(0)) {
            bank = BankFindUtils.findByIdOrElseThrow(request.getBankId(), this.bankRepository);
        }

        this.companyRepository.save(CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository).update(requestCompany, bank));
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

    public void updateUserMember(BusinessAccountUserDto.Request.UserMemberUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getId(), this.businessAccountUserRepository);

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

        // 비즈니스 회원 상태 업데이트
        if(request.getMemberType() == BusinessAccountUser.MemberType.MASTER) {
            businessAccountUser.changeMemberTypeMaster();
        } else if(request.getMemberType() == BusinessAccountUser.MemberType.OPERATOR) {
            businessAccountUser.changeMemberTypeOperator();
        } else if(request.getMemberType() == BusinessAccountUser.MemberType.MEMBER) {
            businessAccountUser.changeMemberTypeMember();
        }
    }

    public void updateUserStatus(BusinessAccountUserDto.Request.UserStatusUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo = this.businessAccountUserRepository.businessAccountUserInfo(request.getBusinessAccountId(), loginUserNo);
        if(businessAccountUserInfo.getMemberType() != BusinessAccountUser.MemberType.MASTER) {
            throw new BusinessAccountUserAuthorizationException();
        }

        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getId(), this.businessAccountUserRepository);

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

        // 비즈니스 회원 상태 업데이트
        if(request.getStatus() == BusinessAccountUser.Status.Y) {
            businessAccountUser.changeStatusY();
        } else if(request.getStatus() == BusinessAccountUser.Status.R) {
            businessAccountUser.changeStatusR();
        } else if(request.getStatus() == BusinessAccountUser.Status.C) {
            businessAccountUser.changeStatusC();
        }
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
        this.businessAccountUserRepository.updateAccounting(businessAccountUser.getBusinessAccount().getId(), loginUserNo, BusinessAccountUser.AccountingYN.N);

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

        // 권한 변경
        this.businessAccountUserRepository.updateAccounting(businessAccountUser2.getBusinessAccount().getId(), request.getId(), BusinessAccountUser.AccountingYN.Y);
    }

    public void updateUserAdminAccounting(BusinessAccountUserDto.Request.UserUpdate request, Integer loginUserNo) {

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
        BusinessAccountUser businessAccountUser = BusinessAccountUserQueryUtils.findByBusinessAccountIdAndUserIdOrElseThrow(request.getBusinessAccountId(), request.getPrevId(), this.businessAccountUserRepository);

        if(businessAccountUser.getAccountingYN() != BusinessAccountUser.AccountingYN.Y) {
            throw new BusinessAccountUserAccountingExistException();
        }

        // 히스토리 저장
        BusinessAccountUserInfoHistoryDto.Request.Save history = new BusinessAccountUserInfoHistoryDto.Request.Save();
        history.setBusinessAccountId(businessAccountUser.getBusinessAccount().getId());
        history.setUserNo(request.getPrevId());
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
        this.businessAccountUserRepository.updateAccounting(businessAccountUser.getBusinessAccount().getId(), request.getPrevId(), BusinessAccountUser.AccountingYN.N);

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

        // 권한 변경
        this.businessAccountUserRepository.updateAccounting(businessAccountUser2.getBusinessAccount().getId(), request.getId(), BusinessAccountUser.AccountingYN.Y);
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

        // 비즈니스 계정 삭제
        this.businessAccountUserRepository.deleteByBusinessAccountIdAndUserId(businessAccountUser.getBusinessAccount().getId(), businessAccountUser.getUser().getId());

        // 광고 계정 삭제
        List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserInfo = this.businessAccountUserRepository.adAccountByBusinessAccountIdAndUserId(businessAccountUser.getBusinessAccount().getId(), businessAccountUser.getUser().getId());

        for (AdAccountUserDto.Response.AdAccountUserInfo co: adAccountUserInfo) {
            AdAccountUser adAccountUser = AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(co.getId(), co.getUser().getId(), this.adAccountUserRepository);

            // 히스토리 저장
            AdAccountUserInfoHistoryDto.Request.Save history2 = new AdAccountUserInfoHistoryDto.Request.Save();
            history2.setAdAccountId(adAccountUser.getAdAccount().getId());
            history2.setUserNo(adAccountUser.getUser().getId());
            history2.setMemberType(adAccountUser.getMemberType());
            history2.setStatus(AdAccountUser.Status.D);
            history2.setRegUserNo(adAccountUser.getCreatedUserNo());
            history2.setCreatedAt(adAccountUser.getCreatedAt());
            history2.setUpdUserNo(adAccountUser.getUpdatedUserNo());
            history2.setUpdatedAt(adAccountUser.getUpdatedAt());
            AdAccountUserInfoHistory adAccountUserInfoHistory = this.adAccountUserInfoHistoryMapper.toEntity(history2, SecurityUtils.getLoginUserNo());
            this.adAccountUserInfoHistoryRepository.save(adAccountUserInfoHistory);

            // 삭제
            this.adAccountUserRepository.deleteByAdAccountIdAndUserId(adAccountUser.getAdAccount().getId(), adAccountUser.getUser().getId());
        }
    }

    public void creditLimitUpdate(BusinessAccountDto.Request.CreditLimitUpdate request) {
        BusinessAccountFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountRepository).creditLimitUpdate(request);
    }

    public void changeConfig(Integer id, BusinessAccount.Config config) {
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(id, this.businessAccountRepository);
        if (config == BusinessAccount.Config.ON) businessAccount.changeConfigOn();
        else if (config == BusinessAccount.Config.OFF) businessAccount.changeConfigOff();
    }

    public void updateRefundAccount(BusinessAccountDto.Request.UpdateRefundAccount request, Integer loginUserNo) {
        this.businessAccountQueryMapper.updateRefundAccount(request, loginUserNo);
    }

    public void updateTaxIssue(TaxBillDto.Request.BusinessTaxBillUpdate request, Integer loginUserNo) {
        BusinessAccountTaxBill businessAccountTaxBill = BusinessAccountTaxBillFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountTaxBillRepository);
        if(!businessAccountTaxBill.isIssueStatus()) {
            businessAccountTaxBill.update(request);
            if(request.getBusinessAccountTaxBillFiles().size() > 0) {
                request.getBusinessAccountTaxBillFiles().forEach(file -> businessAccountTaxBill.addBusinessAccountTaxBillFile(this.saveBusinessAccountTaxBillFile(request, businessAccountTaxBill, file)));
            }
//            this.businessAccountQueryMapper.updateTaxIssue(request, loginUserNo);
        }

    }

    @SneakyThrows
    private BusinessAccountTaxBillFile saveBusinessAccountTaxBillFile(TaxBillDto.Request.BusinessTaxBillUpdate request, BusinessAccountTaxBill businessAccountTaxBill, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
        String savedFileUrl = this.awsFileService.saveBusinessAccountTaxBillFile(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new BusinessAccountTaxBillFile(businessAccountTaxBill, this.mediaTaxBillFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation mediaTaxBillFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
        } else if (mimetype.startsWith("application/pdf")) {
            fileType = FileInformation.FileType.PDF;
        } else {
            throw new UnsupportedOperationException();
        }

        return FileInformation.builder()
                .url(savedFileUrl)
                .fileType(fileType)
                .fileSize(file.getSize())
                .filename(savedFilename)
                .originalFileName(originalFilename)
                .mimeType(mimetype)
                .build();
    }

}
