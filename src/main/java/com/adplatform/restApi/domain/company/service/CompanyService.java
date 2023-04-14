package com.adplatform.restApi.domain.company.service;

import com.adplatform.restApi.domain.bank.dao.BankRepository;
import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.service.BankFindUtils;
import com.adplatform.restApi.domain.company.dao.user.AdminUserRepository;
import com.adplatform.restApi.domain.company.dao.user.MediaCompanyUserTransferRepository;
import com.adplatform.restApi.domain.company.domain.*;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dao.user.MediaCompanyUserRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.dto.user.*;
import com.adplatform.restApi.domain.company.exception.*;
import com.adplatform.restApi.domain.history.dao.admin.user.AdminUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.dao.mediaCompany.user.MediaCompanyUserInfoHistoryRepository;
import com.adplatform.restApi.domain.history.domain.admin.user.AdminUserInfoHistory;
import com.adplatform.restApi.domain.history.domain.mediaCompany.user.MediaCompanyUserInfoHistory;
import com.adplatform.restApi.domain.history.dto.admin.user.AdminUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.admin.user.AdminUserInfoHistoryMapper;
import com.adplatform.restApi.domain.history.dto.mediaCompany.user.MediaCompanyUserInfoHistoryDto;
import com.adplatform.restApi.domain.history.dto.mediaCompany.user.MediaCompanyUserInfoHistoryMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.exception.UserNotFoundException;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.value.Email;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import com.adplatform.restApi.infra.file.service.FileService;
import com.adplatform.restApi.infra.mail.event.FindPasswordEmailSentEvent;
import com.adplatform.restApi.infra.mail.event.MediaCompanyInviteEmailSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final BankRepository bankRepository;
    private final CompanyMapper companyMapper;
    private final FileService fileService;
    private final UserQueryService userQueryService;
    private final MediaCompanyUserRepository mediaCompanyUserRepository;
    private final MediaCompanyUserMapper mediaCompanyUserMapper;
    private final MediaCompanyUserInfoHistoryMapper mediaCompanyUserInfoHistoryMapper;
    private final MediaCompanyUserInfoHistoryRepository mediaCompanyUserInfoHistoryRepository;
    private final MediaCompanyUserTransferMapper mediaCompanyUserTransferMapper;
    private final MediaCompanyUserTransferRepository mediaCompanyUserTransferRepository;
    private final AdminUserRepository adminUserRepository;
    private final AdminUserMapper adminUserMapper;
    private final AdminUserInfoHistoryMapper adminUserInfoHistoryMapper;
    private final AdminUserInfoHistoryRepository adminUserInfoHistoryRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AwsFileService awsFileService;

    public Company findByIdOrElseThrow(Integer id) {
        return this.companyRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void saveMedia(CompanyDto.Request.Save request) {
        Integer count = this.companyRepository.findByRegistrationNumberCount(request.getRegistrationNumber());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        if(request.getBankId().equals(0)) {
            Company company = this.companyMapper.toMediaNoBankEntity(request);
            request.getBusinessFiles().forEach(file -> company.addBusinessFile(this.saveCompanyFile(request, company, file, "BUSINESS")));
            request.getBankFiles().forEach(file -> company.addBankFile(this.saveCompanyFile(request, company, file, "BANK")));
            this.companyRepository.save(company);
        } else {
            Bank bank = BankFindUtils.findByIdOrElseThrow(request.getBankId(), this.bankRepository);

            Company company = this.companyMapper.toMediaEntity(request, bank);
            request.getBusinessFiles().forEach(file -> company.addBusinessFile(this.saveCompanyFile(request, company, file, "BUSINESS")));
            request.getBankFiles().forEach(file -> company.addBankFile(this.saveCompanyFile(request, company, file, "BANK")));
            this.companyRepository.save(company);
        }
    }

    public void updateMedia(CompanyDto.Request.Update request) {
        try{
            Bank bank = BankFindUtils.findByIdOrElseThrow(request.getBankId(), this.bankRepository);

            Company company = CompanyFindUtils.findByIdOrElseThrow(request.getId(), this.companyRepository).update(request, bank);
            if(request.getBusinessFiles().size() > 0) {
                request.getBusinessFiles().forEach(file -> company.addBusinessFile(this.saveCompanyFile(request, company, file, "BUSINESS")));
            }
            if(request.getBankFiles().size() > 0) {
                request.getBankFiles().forEach(file -> company.addBankFile(this.saveCompanyFile(request, company, file, "BANK")));
            }
//            this.companyRepository.save(company);
        }catch (Exception e){
            throw new CompanyUpdateException();
        }
    }

    @SneakyThrows
    private CompanyFile saveCompanyFile(CompanyDto.Request.Save request, Company company, MultipartFile file, String fType) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.saveCompany(request, file, fType);
        String savedFileUrl = this.awsFileService.saveCompany(request, file, fType);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new CompanyFile(company, fType, this.companyFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation companyFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
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

    public void delete(Integer id) {
        CompanyFindUtils.findByIdOrElseThrow(id, this.companyRepository).delete();
    }

    public void saveUser(MediaCompanyUserDto.Request.SaveUser request, Integer loginUserNo) {

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        if(userInfo == null) {
            throw new UserNotFoundException();
        }
        Integer count = this.mediaCompanyUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        Integer count1 = this.mediaCompanyUserRepository.findByCompanyIdCount(request.getCompanyId());
        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        if(count1.equals(0)) {
            MediaCompanyUser mediaCompanyUser = this.mediaCompanyUserMapper.toEntity(request, company, user);
            this.mediaCompanyUserRepository.save(mediaCompanyUser);
        } else {
            MediaCompanyUser mediaCompanyUser = this.mediaCompanyUserMapper.toEntityInvite(request, company, user);
            this.mediaCompanyUserRepository.save(mediaCompanyUser);
        }


    }

    public void saveUserInvite(MediaCompanyUserDto.Request.SaveUser request, Integer loginUserNo) {

        // MASTER 권한 체크
        MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo = this.mediaCompanyUserRepository.mediaCompanyUserInfo(request.getCompanyId(), loginUserNo);
        if(mediaCompanyUserInfo.getMemberType() != MediaCompanyUser.MemberType.MASTER)  {
            throw new MediaCompanyUserAuthorizationException();
        }

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        if(userInfo == null) {
            throw new UserNotFoundException();
        }
        Integer count = this.mediaCompanyUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        MediaCompanyUser mediaCompanyUser = this.mediaCompanyUserMapper.toEntityInvite(request, company, user);
        this.mediaCompanyUserRepository.save(mediaCompanyUser);

        this.eventPublisher.publishEvent(new MediaCompanyInviteEmailSentEvent(new Email(userInfo.getLoginId()), mediaCompanyUserInfo.getUser().getName(), mediaCompanyUser.getCompany().getName()));
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

        // 매체사 회원 상태 업데이트
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

        // 매체사 회원 상태 업데이트
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

        // 이양 로그
        MediaCompanyUserTransfer mediaCompanyUserTransfer = this.mediaCompanyUserTransferMapper.toEntity(request.getCompanyId(), loginUserNo, request.getId());
        this.mediaCompanyUserTransferRepository.save(mediaCompanyUserTransfer);
    }

    public void updateUserAdminAccounting(MediaCompanyUserDto.Request.UserUpdate request, Integer loginUserNo) {

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
        MediaCompanyUser mediaCompanyUser = MediaCompanyUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getPrevId(), this.mediaCompanyUserRepository);

        if(mediaCompanyUser.getAccountingYN() != MediaCompanyUser.AccountingYN.Y) {
            throw new MediaCompanyUserAccountingExistException();
        }

        // 히스토리 저장
        MediaCompanyUserInfoHistoryDto.Request.Save history = new MediaCompanyUserInfoHistoryDto.Request.Save();
        history.setCompanyId(mediaCompanyUser.getCompany().getId());
        history.setUserNo(request.getPrevId());
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
        this.mediaCompanyUserRepository.updateAccounting(mediaCompanyUser.getCompany().getId(), request.getPrevId(), MediaCompanyUser.AccountingYN.N);

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

        // 이양 로그
        MediaCompanyUserTransfer mediaCompanyUserTransfer = this.mediaCompanyUserTransferMapper.toEntity(request.getCompanyId(), request.getPrevId(), request.getId());
        this.mediaCompanyUserTransferRepository.save(mediaCompanyUserTransfer);
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


    public void saveUserAdmin(AdminUserDto.Request.SaveUser request, Integer loginUserNo) {

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        if(userInfo == null) {
            throw new UserNotFoundException();
        }
        Integer count = this.adminUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new MediaCompanyUserAlreadyExistException();
        }

        Integer count1 = this.adminUserRepository.findByCompanyIdCount(request.getCompanyId());
        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        AdminUser adminUser;
        if(count1.equals(0)) {
            adminUser = this.adminUserMapper.toEntity(request, company, user);
            this.adminUserRepository.save(adminUser);
        } else {
            adminUser = this.adminUserMapper.toEntityInvite(request, company, user);
            this.adminUserRepository.save(adminUser);
        }
    }

    public void saveUserAdminInvite(AdminUserDto.Request.SaveUser request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdminUserDto.Response.AdminUserInfo adminUserInfo = this.adminUserRepository.adminUserInfo(request.getCompanyId(), loginUserNo);
        if(adminUserInfo.getMemberType() != AdminUser.MemberType.MASTER)  {
            throw new AdminUserAuthorizationException();
        }

        // 회원 중복 체크
        UserDto.Response.BaseInfo userInfo = this.userQueryService.findUserByLoginId(request.getUserId());
        if(userInfo == null) {
            throw new UserNotFoundException();
        }
        Integer count = this.adminUserRepository.findByCompanyIdAndUserIdCount(request.getCompanyId(), userInfo.getId());

        if(!count.equals(0)) {
            throw new AdminUserAlreadyExistException();
        }

        // 인서트
        User user = this.userQueryService.findByIdOrElseThrow(userInfo.getId());
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        AdminUser adminUser = this.adminUserMapper.toEntityInvite(request, company, user);
        this.adminUserRepository.save(adminUser);
    }

    public void updateUserAdminMember(AdminUserDto.Request.UserMemberUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdminUserDto.Response.AdminUserInfo adminUserInfo = this.adminUserRepository.adminUserInfo(request.getCompanyId(), loginUserNo);
        if(adminUserInfo.getMemberType() != AdminUser.MemberType.MASTER) {
            throw new AdminUserAuthorizationException();
        }

        AdminUser adminUser = AdminUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.adminUserRepository);

        // 히스토리 저장
        AdminUserInfoHistoryDto.Request.Save history = new AdminUserInfoHistoryDto.Request.Save();
        history.setCompanyId(adminUser.getCompany().getId());
        history.setUserNo(adminUser.getUser().getId());
        history.setMemberType(adminUser.getMemberType());
        history.setStatus(adminUser.getStatus());
        history.setRegUserNo(adminUser.getCreatedUserNo());
        history.setCreatedAt(adminUser.getCreatedAt());
        history.setUpdUserNo(adminUser.getUpdatedUserNo());
        history.setUpdatedAt(adminUser.getUpdatedAt());
        AdminUserInfoHistory adminUserInfoHistory = this.adminUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adminUserInfoHistoryRepository.save(adminUserInfoHistory);

        // 관리자 회원 상태 업데이트
        if(request.getMemberType() == AdminUser.MemberType.MASTER) {
            adminUser.changeMemberTypeMaster();
        } else if(request.getMemberType() == AdminUser.MemberType.OPERATOR) {
            adminUser.changeMemberTypeOperator();
        } else if(request.getMemberType() == AdminUser.MemberType.MEMBER) {
            adminUser.changeMemberTypeMember();
        }
    }

    public void updateUserAdminStatus(AdminUserDto.Request.UserStatusUpdate request, Integer loginUserNo) {

        // MASTER 권한 체크
        AdminUserDto.Response.AdminUserInfo adminUserInfo = this.adminUserRepository.adminUserInfo(request.getCompanyId(), loginUserNo);
        if(adminUserInfo.getMemberType() != AdminUser.MemberType.MASTER) {
            throw new AdminUserAuthorizationException();
        }

        AdminUser adminUser = AdminUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.adminUserRepository);

        // 히스토리 저장
        AdminUserInfoHistoryDto.Request.Save history = new AdminUserInfoHistoryDto.Request.Save();
        history.setCompanyId(adminUser.getCompany().getId());
        history.setUserNo(adminUser.getUser().getId());
        history.setMemberType(adminUser.getMemberType());
        history.setStatus(adminUser.getStatus());
        history.setRegUserNo(adminUser.getCreatedUserNo());
        history.setCreatedAt(adminUser.getCreatedAt());
        history.setUpdUserNo(adminUser.getUpdatedUserNo());
        history.setUpdatedAt(adminUser.getUpdatedAt());
        AdminUserInfoHistory adminUserInfoHistory = this.adminUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adminUserInfoHistoryRepository.save(adminUserInfoHistory);

        // 관리자 회원 상태 업데이트
        if(request.getStatus() == AdminUser.Status.Y) {
            adminUser.changeStatusY();
        } else if(request.getStatus() == AdminUser.Status.R) {
            adminUser.changeStatusR();
        } else if(request.getStatus() == AdminUser.Status.C) {
            adminUser.changeStatusC();
        }
    }

    public void deleteUserAdmin(AdminUserDto.Request.UserUpdate request, Integer loginUserNo) {
        // 권한 체크
        AdminUserDto.Response.AdminUserInfo adminUserInfo = this.adminUserRepository.adminUserInfo(request.getCompanyId(), loginUserNo);
        // 본인 여부 체크
        if(!adminUserInfo.getUser().getId().equals(request.getId())) {
            // MASTER 권한 체크
            if(adminUserInfo.getMemberType() != AdminUser.MemberType.MASTER) {
                throw new AdminUserAuthorizationException();
            }
        }

        AdminUser adminUser = AdminUserQueryUtils.findByCompanyIdAndUserIdOrElseThrow(request.getCompanyId(), request.getId(), this.adminUserRepository);

        // 히스토리 저장
        AdminUserInfoHistoryDto.Request.Save history = new AdminUserInfoHistoryDto.Request.Save();
        history.setCompanyId(adminUser.getCompany().getId());
        history.setUserNo(adminUser.getUser().getId());
        history.setMemberType(adminUser.getMemberType());
        history.setStatus(AdminUser.Status.D);
        history.setRegUserNo(adminUser.getCreatedUserNo());
        history.setCreatedAt(adminUser.getCreatedAt());
        history.setUpdUserNo(adminUser.getUpdatedUserNo());
        history.setUpdatedAt(adminUser.getUpdatedAt());
        AdminUserInfoHistory adminUserInfoHistory = this.adminUserInfoHistoryMapper.toEntity(history, SecurityUtils.getLoginUserNo());
        this.adminUserInfoHistoryRepository.save(adminUserInfoHistory);

        // 삭제
        this.adminUserRepository.deleteByCompanyIdAndUserIdCount(adminUser.getCompany().getId(), adminUser.getUser().getId());
    }
}
