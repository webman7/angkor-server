package com.adplatform.restApi.global.config.security.exception.handler;

import com.adplatform.restApi.global.config.security.constant.JwtProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        Object jwtException = request.getAttribute(JwtProperties.JWT_EXCEPTION);
        if (jwtException == null) response.sendRedirect("/exception/authentication");
        else if (((int) jwtException) == JwtProperties.EXPIRED_ACCESS_TOKEN_EXCEPTION_CODE)
            response.sendRedirect("/exception/expired_access_token");
    }
}
