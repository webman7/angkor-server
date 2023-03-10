package com.adplatform.restApi.global.config.security.filter;

import com.adplatform.restApi.global.config.security.constant.JwtProperties;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author junny
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        try {
            this.validateToken((HttpServletRequest) request, (HttpServletResponse) response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 Jwt 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
            request.setAttribute(JwtProperties.JWT_EXCEPTION, JwtProperties.EXPIRED_ACCESS_TOKEN_EXCEPTION_CODE);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 토큰입니다.");
        }
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = this.jwtProvider.resolveToken(request);
        if (token != null && this.jwtProvider.validateToken(token)) {
            Authentication authentication = this.jwtProvider.getAuthentication(token);
            response.addHeader(
                    HttpHeaders.AUTHORIZATION,
                    this.jwtProvider.createToken(authentication.getName()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
