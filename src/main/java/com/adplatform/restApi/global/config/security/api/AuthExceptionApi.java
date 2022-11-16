package com.adplatform.restApi.global.config.security.api;

import com.adplatform.restApi.global.config.security.exception.AccessDeniedException;
import com.adplatform.restApi.global.config.security.exception.AuthenticationEntryPointException;
import com.adplatform.restApi.global.config.security.exception.ExpiredAccessTokenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RestController
@RequestMapping("/exception")
public class AuthExceptionApi {
    @GetMapping("/expired_access_token")
    public void expiredAccessToken() {
        throw new ExpiredAccessTokenException();
    }

    @GetMapping("/authentication")
    public void authenticationException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/access_denied")
    public void accessDeniedException() {
        throw new AccessDeniedException();
    }
}
