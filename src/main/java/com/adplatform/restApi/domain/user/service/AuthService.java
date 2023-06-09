package com.adplatform.restApi.domain.user.service;

import com.adplatform.restApi.domain.history.dto.user.UserLoginHistoryDto;
import com.adplatform.restApi.domain.history.dto.user.UserLoginHistoryMapper;
import com.adplatform.restApi.domain.history.dto.user.UserPasswordChangeHistoryDto;
import com.adplatform.restApi.domain.history.dto.user.UserPasswordChangeHistoryMapper;
import com.adplatform.restApi.domain.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.exception.*;
import com.adplatform.restApi.global.util.HttpReqRespUtils;
import com.adplatform.restApi.domain.history.dao.user.UserLoginHistoryRepository;
import com.adplatform.restApi.domain.history.dao.user.UserPasswordChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.UserLoginHistory;
import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.auth.AuthMapper;
import com.adplatform.restApi.global.config.security.dto.TokenDto;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.util.RandomCodeGenerator;
import com.adplatform.restApi.global.value.Email;
import com.adplatform.restApi.infra.mail.event.FindPasswordEmailSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;
    private final UserQueryService userQueryService;
    private final UserLoginHistoryMapper userLoginHistoryMapper;
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final UserPasswordChangeHistoryMapper userPasswordChangeHistoryMapper;
    private final UserPasswordChangeHistoryRepository userPasswordChangeHistoryRepository;
    private final UserQueryMapper userQueryMapper;

    @Transactional(noRollbackFor = {UserLoginFailedException.class, PasswordWrongCountExceededException.class})
    public TokenDto login(AuthDto.Request.Login request) {
        User user = this.userQueryService.findByLoginIdOrElseThrow(request.getId());

        this.validateUserActive(user.getActive(), request.getId());
        try {
            if (!user.getPassword().validate(this.passwordEncoder, request.getPassword())) {
                UserLoginHistoryDto.Request.Save history = new UserLoginHistoryDto.Request.Save();
                history.setLoginType(1);
                history.setUserId(request.getId());
                history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
                UserLoginHistory userLoginHistory = this.userLoginHistoryMapper.toEntity(history);
                this.userLoginHistoryRepository.save(userLoginHistory);

                throw new UserLoginFailedException();
            }
        } catch (PasswordWrongCountExceededException e) {
            user.updateActive(User.Active.L);
            UserLoginHistoryDto.Request.Save history = new UserLoginHistoryDto.Request.Save();
            history.setLoginType(1);
            history.setUserId(request.getId());
            history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
            UserLoginHistory userLoginHistory = this.userLoginHistoryMapper.toEntity(history);
            this.userLoginHistoryRepository.save(userLoginHistory);
            throw new PasswordWrongCountExceededException();
        }

        // 사이트별 로그인 확인
        if(request.getUserSite() == null || request.getUserSite().equals("advertiser")) {
            request.setUserSite("advertiser");
        } else if(request.getUserSite().equals("admin")) {
            int count = this.userQueryMapper.getAdminUserCount(user.getId());
            if(count == 0) {
                throw new UserNotFoundException();
            }
        } else if(request.getUserSite().equals("media")) {
            int count = this.userQueryMapper.getMediaCompanyUserCount(user.getId());
            if(count == 0) {
                throw new UserNotFoundException();
            }
        } else {
            request.setUserSite("advertiser");
        }


        UserLoginHistoryDto.Request.Save history = new UserLoginHistoryDto.Request.Save();
        history.setLoginType(0);
        history.setUserId(request.getId());
        history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        UserLoginHistory userLoginHistory = this.userLoginHistoryMapper.toEntity(history);
        this.userLoginHistoryRepository.save(userLoginHistory);
        return this.jwtProvider.createTokenDto(user, request.getUserSite());
    }

    private void validateUserActive(User.Active active, String userId) {
        UserLoginHistoryDto.Request.Save history = new UserLoginHistoryDto.Request.Save();
        switch (active) {
            case W:
                history.setLoginType(4);
                history.setUserId(userId);
                history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
                UserLoginHistory userLoginHistory = this.userLoginHistoryMapper.toEntity(history);
                this.userLoginHistoryRepository.save(userLoginHistory);
                throw new WaitingApprovalUserException();
            case R:
                history.setLoginType(5);
                history.setUserId(userId);
                history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
                UserLoginHistory userLoginHistory1 = this.userLoginHistoryMapper.toEntity(history);
                this.userLoginHistoryRepository.save(userLoginHistory1);
                throw new RejectUserException();
            case N:
                history.setLoginType(2);
                history.setUserId(userId);
                history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
                UserLoginHistory userLoginHistory2 = this.userLoginHistoryMapper.toEntity(history);
                this.userLoginHistoryRepository.save(userLoginHistory2);
                throw new WithdrawUserException();
            case L:
                history.setLoginType(3);
                history.setUserId(userId);
                history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
                UserLoginHistory userLoginHistory3 = this.userLoginHistoryMapper.toEntity(history);
                this.userLoginHistoryRepository.save(userLoginHistory3);
                throw new PasswordWrongCountExceededException();
        }
    }

    public void signup(AuthDto.Request.SignUp request) {
        this.userQueryService.validateExistsByLoginId(request);
        this.validateIsEqualPassword(request.getPassword1(), request.getPassword2());
        User user = this.authMapper.toEntity(request, this.passwordEncoder);
        this.userRepository.save(user);
    }

    public UserDto.Response.BaseInfo findUser(AuthDto.Request.FindPassword request) {
        return this.userQueryService.findUserByLoginId(request.getId());
    }
    public void findPasswordCert(AuthDto.Request.FindPasswordCert request) {
//        String randomPassword = this.userQueryService.findByLoginIdAndName(request.getId(), request.getName())
//                .getPassword()
//                .changeToRandomPassword(this.passwordEncoder);

        this.userQueryService.findByLoginId(request.getId());

        RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
        String randomCertNo = randomCodeGenerator.generate(10);

        // Password Change Log
        UserPasswordChangeHistoryDto.Request.Save history = new UserPasswordChangeHistoryDto.Request.Save();
        history.setUserId(request.getId());
        history.setCertNo(randomCertNo);
        history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        UserPasswordChangeHistory userPasswordChangeHistory = this.userPasswordChangeHistoryMapper.toEntity(history);
        this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory);

        this.eventPublisher.publishEvent(new FindPasswordEmailSentEvent(new Email(request.getId()), randomCertNo));
    }

    public UserPasswordChangeHistory findPasswordConfirm(AuthDto.Request.FindPasswordConfirm request) {
        UserPasswordChangeHistory userPasswordChangeHistory = this.userQueryService.findPasswordConfirm(request);
        this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory.updateStatus(UserPasswordChangeHistory.Status.WAITING));
        return userPasswordChangeHistory;
    }

    public void findPasswordChange(AuthDto.Request.FindPasswordChange request) {
        UserPasswordChangeHistory userPasswordChangeHistory = this.userQueryService.findPasswordChange(request);

        this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory.updateFinished(UserPasswordChangeHistory.Status.FINISHED, HttpReqRespUtils.getClientIpAddressIfServletRequestExist()));

        this.userQueryService.findByLoginId(request.getId())
                .getPassword()
                .changePassword(this.passwordEncoder, request.getPassword());
    }

    public void changePassword(AuthDto.Request.ChangePassword request) {
        User user = this.userQueryService.findByLoginIdOrElseThrow(SecurityUtils.getLoginUserLoginId());
        if (!user.getPassword().validate(this.passwordEncoder, request.getOldPassword())) {
            throw new PasswordNotEqualException();
        } else {
            this.userQueryService.findByLoginIdOrElseThrow(SecurityUtils.getLoginUserLoginId())
                    .getPassword()
                    .changePassword(this.passwordEncoder, request.getNewPassword());
            // Password Change Log
            UserPasswordChangeHistoryDto.Request.Save history = new UserPasswordChangeHistoryDto.Request.Save();
            history.setUserId(SecurityUtils.getLoginUserLoginId());
            history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
            UserPasswordChangeHistory userPasswordChangeHistory = this.userPasswordChangeHistoryMapper.toEntity(history);
            this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory);
        }
    }

    private void validateIsEqualPassword(String password1, String password2) {
        if (!password1.equals(password2)) throw new PasswordNotEqualException();
    }
}
