package com.adplatform.restApi.global.config.security.config;

import com.adplatform.restApi.global.config.security.exception.handler.CustomAccessDeniedHandler;
import com.adplatform.restApi.global.config.security.exception.handler.CustomAuthenticationEntryPoint;
import com.adplatform.restApi.global.config.security.filter.JwtAuthenticationFilter;
import com.adplatform.restApi.global.config.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.adplatform.restApi.domain.user.domain.Role.Type.*;

@Profile({"dev", "prod"})
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().disable()
                .csrf().disable()
                .cors().configurationSource(this.corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/users/**").hasAuthority(ROLE_ADMIN.name())
                .antMatchers("/companies/**").hasAuthority(ROLE_ADMIN.name())
                .antMatchers(HttpMethod.POST, "/campaign")
                .hasAnyAuthority(ROLE_ADMIN.name(), ROLE_OPERATOR.name(), ROLE_COMPANY_ADMINISTRATOR.name(), ROLE_COMPANY_GENERAL.name())
                .antMatchers(HttpMethod.GET, "/media", "/device").authenticated()
                .antMatchers(HttpMethod.POST, "/change-password").authenticated()
                .antMatchers(HttpMethod.POST, "/signup", "/login", "/find-password").permitAll()
                .antMatchers(HttpMethod.GET, "/exception/**", "/").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.customAuthenticationEntryPoint)
                .accessDeniedHandler(this.customAccessDeniedHandler)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
