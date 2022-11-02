package com.adplatform.restApi.domain.user.service;

import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.Role;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.domain.UserRole;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.auth.AuthMapper;
import com.adplatform.restApi.domain.user.exception.*;
import com.adplatform.restApi.global.config.security.dto.TokenDto;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import com.adplatform.restApi.global.config.security.util.SecurityUtil;
import com.adplatform.restApi.global.value.Email;
import com.adplatform.restApi.infra.mail.event.FindPasswordEmailSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;
    private final UserSearchService userSearchService;

    @Transactional(noRollbackFor = {UserLoginFailedException.class, PasswordWrongCountExceededException.class})
    public TokenDto login(AuthDto.Request.Login request) {
        User user = this.userSearchService.findByIdOrElseThrow(request.getId());
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
        this.userSearchService.validateExistsByLoginId(request);
        User user = this.authMapper.toEntity(request, this.passwordEncoder);
        Role role = this.userSearchService.findRoleByType(Role.Type.ROLE_ADMIN);
        this.userRepository.save(user.updateRole(new UserRole(user, role)));
    }

    public void findPassword(AuthDto.Request.FindPassword request) {
        String randomPassword = this.userSearchService.findByLoginIdAndName(request.getId(), request.getName())
                .updateActive(User.Active.Y)
                .getPassword()
                .changeToRandomPassword(this.passwordEncoder);
        this.eventPublisher.publishEvent(new FindPasswordEmailSentEvent(new Email(request.getId()), randomPassword));
    }

    public void changePassword(AuthDto.Request.ChangePassword request) {
        if (!this.isEqualPassword(request.getPassword1(), request.getPassword2()))
            throw new ChangePasswordNotEqualException();
        this.userSearchService.findByIdOrElseThrow(SecurityUtil.getLoginUserLoginId())
                .updateActive(User.Active.Y)
                .getPassword()
                .changePassword(this.passwordEncoder, request.getPassword1());
    }

    private boolean isEqualPassword(String password1, String password2) {
        return password1.equals(password2);
    }
}
