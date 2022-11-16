package com.adplatform.restApi.global.config.security.config;

import com.adplatform.restApi.global.config.security.exception.handler.CustomAccessDeniedHandler;
import com.adplatform.restApi.global.config.security.exception.handler.CustomAuthenticationEntryPoint;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Profile("local")
@Configuration
public class LocalSecurityConfig extends SecurityConfig {
    public LocalSecurityConfig(
            JwtProvider jwtProvider,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
        super(jwtProvider, customAuthenticationEntryPoint, customAccessDeniedHandler);
    }

    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(new WhiteListedAllowFromStrategy(List.of("localhost"))))
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll();
        return super.securityFilterChain(http);
    }
}
