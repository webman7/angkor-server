package com.adplatform.restApi.user.service;

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

    @Transactional(noRollbackFor = {UserLoginFailedException.class, PasswordWrongCountExceededException.class})
    public TokenDto login(AuthDto.Request.Login request) {
        User user = this.userQueryService.findByLoginIdOrElseThrow(request.getId());
        this.validateUserActive(user.getActive());
        try {
            if (!user.getPassword().validate(this.passwordEncoder, request.getPassword()))
                throw new UserLoginFailedException();
        } catch (PasswordWrongCountExceededException e) {
            user.updateActive(User.Active.L);
            throw new PasswordWrongCountExceededException();
        }
        return this.jwtProvider.createTokenDto(user);
    }

    private void validateUserActive(User.Active active) {
        switch (active) {
            case W: throw new WaitingApprovalUserException();
            case N: throw new WithdrawUserException();
            case L: throw new PasswordWrongCountExceededException();
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
                .updateActive(User.Active.Y)
                .getPassword()
                .changeToRandomPassword(this.passwordEncoder);
        this.eventPublisher.publishEvent(new FindPasswordEmailSentEvent(new Email(request.getId()), randomPassword));
    }

    public void changePassword(AuthDto.Request.ChangePassword request) {
        this.validateIsEqualPassword(request.getPassword1(), request.getPassword2());
        this.userQueryService.findByLoginIdOrElseThrow(SecurityUtils.getLoginUserLoginId())
                .updateActive(User.Active.Y)
                .getPassword()
                .changePassword(this.passwordEncoder, request.getPassword1());
    }

    private void validateIsEqualPassword(String password1, String password2) {
        if (!password1.equals(password2)) throw new PasswordNotEqualException();
    }
}
