package com.adplatform.restApi.user.service;

import com.adplatform.restApi.global.util.HttpReqRespUtils;
import com.adplatform.restApi.history.dao.user.UserLoginHistoryRepository;
import com.adplatform.restApi.history.dao.user.UserPasswordChangeHistoryRepository;
import com.adplatform.restApi.history.domain.UserLoginHistory;
import com.adplatform.restApi.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.history.domain.UserRolesChangeHistory;
import com.adplatform.restApi.history.dto.campaign.*;
import com.adplatform.restApi.user.dao.UserRepository;
import com.adplatform.restApi.user.domain.Role;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.user.domain.UserRole;
import com.adplatform.restApi.user.dto.auth.AuthDto;
import com.adplatform.restApi.user.dto.auth.AuthMapper;
import com.adplatform.restApi.user.exception.*;
import com.adplatform.restApi.global.config.security.dto.TokenDto;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.value.Email;
import com.adplatform.restApi.infra.mail.event.FindPasswordEmailSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seohyun Lee
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
        UserLoginHistoryDto.Request.Save history = new UserLoginHistoryDto.Request.Save();
        history.setLoginType(0);
        history.setUserId(request.getId());
        history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        UserLoginHistory userLoginHistory = this.userLoginHistoryMapper.toEntity(history);
        this.userLoginHistoryRepository.save(userLoginHistory);
        return this.jwtProvider.createTokenDto(user);
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
        Role role = this.userQueryService.findRoleByType(Role.Type.ROLE_COMPANY_GENERAL);
        this.userRepository.save(user.updateRole(new UserRole(user, role)));
    }

    public void findPassword(AuthDto.Request.FindPassword request) {
        String randomPassword = this.userQueryService.findByLoginIdAndName(request.getId(), request.getName())
                .getPassword()
                .changeToRandomPassword(this.passwordEncoder);
        this.eventPublisher.publishEvent(new FindPasswordEmailSentEvent(new Email(request.getId()), randomPassword));

        // Password Change Log
        UserPasswordChangeHistoryDto.Request.Save history = new UserPasswordChangeHistoryDto.Request.Save();
        history.setUserId(request.getId());
        history.setUserName(request.getName());
        history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        UserPasswordChangeHistory userPasswordChangeHistory = this.userPasswordChangeHistoryMapper.toEntity(history);
        this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory);
    }

    public void changePassword(AuthDto.Request.ChangePassword request) {
        this.validateIsEqualPassword(request.getPassword1(), request.getPassword2());
        this.userQueryService.findByLoginIdOrElseThrow(SecurityUtils.getLoginUserLoginId())
                .getPassword()
                .changePassword(this.passwordEncoder, request.getPassword1());
        // Password Change Log
        UserPasswordChangeHistoryDto.Request.Save history = new UserPasswordChangeHistoryDto.Request.Save();
        history.setUserId(SecurityUtils.getLoginUserLoginId());
        history.setRegIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
        UserPasswordChangeHistory userPasswordChangeHistory = this.userPasswordChangeHistoryMapper.toEntity(history);
        this.userPasswordChangeHistoryRepository.save(userPasswordChangeHistory);
    }

    private void validateIsEqualPassword(String password1, String password2) {
        if (!password1.equals(password2)) throw new PasswordNotEqualException();
    }
}
