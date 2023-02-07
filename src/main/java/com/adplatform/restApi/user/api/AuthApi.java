package com.adplatform.restApi.user.api;

import com.adplatform.restApi.user.dto.auth.AuthDto;
import com.adplatform.restApi.user.service.AuthService;
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
    @PostMapping("/find-password")
    public void findPassword(@RequestBody @Valid AuthDto.Request.FindPassword request) {
        this.authService.findPassword(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid AuthDto.Request.ChangePassword request) {
        this.authService.changePassword(request);
    }
}
