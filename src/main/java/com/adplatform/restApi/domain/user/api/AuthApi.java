package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.service.AuthService;
import com.adplatform.restApi.global.config.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthDto.Request.Login request) {
        return ResponseEntity.ok(this.authService.login(request));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public void signup(@RequestBody @Valid AuthDto.Request.SignUp request) {
        this.authService.signup(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/find/user")
    public UserDto.Response.BaseInfo findUser(@RequestBody @Valid AuthDto.Request.FindPassword request) {
        return this.authService.findUser(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/find/password/cert")
    public void findPasswordCert(@RequestBody @Valid AuthDto.Request.FindPasswordCert request) {
        this.authService.findPasswordCert(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/find/password/confirm")
    public UserPasswordChangeHistory findPasswordConfirm(@RequestBody @Valid AuthDto.Request.FindPasswordConfirm request) {
        return this.authService.findPasswordConfirm(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/find/password/change")
    public void findPasswordChange(@RequestBody @Valid AuthDto.Request.FindPasswordChange request) {
        this.authService.findPasswordChange(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/password/change")
    public void changePassword(@RequestBody @Valid AuthDto.Request.ChangePassword request) {
        this.authService.changePassword(request);
    }
}
