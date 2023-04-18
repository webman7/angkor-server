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
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.adplatform.restApi.domain.user.domain.Role.Type.*;

/**
 * @author junny
 * @since 1.0
 */
@Profile({"dev", "stage", "prod"})
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        this.securityConfig(http);
        this.userAntMatchers(http);
        this.companyAntMatchers(http);
        this.businessAccountsAntMatchers(http);
        this.adAccountsAntMatchers(http);
        this.campaignAntMatchers(http);
        this.adGroupAntMatchers(http);
        this.creativeAntMatchers(http);
        this.mediaAndDeviceAntMatchers(http);
        this.permitAll(http);
        return http.build();
    }

    private void securityConfig(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().disable()
                .csrf().disable()
                .cors().configurationSource(this.corsConfigurationSource())
                .and()
//                .authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .anyRequest().authenticated()
//                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.customAuthenticationEntryPoint)
                .accessDeniedHandler(this.customAccessDeniedHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private void userAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**").hasAnyAuthority(ROLE_ADMIN.name(), ROLE_OPERATOR.name(), ROLE_MEMBER.name())
                .antMatchers(HttpMethod.POST, "/password/change").authenticated()
                .antMatchers(HttpMethod.POST, "/signup", "/login", "/find/**").permitAll();
    }

    private void companyAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/companies/search/for-signup").permitAll()
                .antMatchers(HttpMethod.GET, "/companies/user/**").permitAll()
                .antMatchers(HttpMethod.POST, "/companies/advertisers").permitAll()
                .antMatchers(HttpMethod.GET, "/companies/registration/number").permitAll()
                .antMatchers(HttpMethod.POST, "/companies/agencies").hasAnyAuthority(ROLE_ADMIN.name())
                .antMatchers("/companies/**").hasAnyAuthority(ROLE_ADMIN.name(), ROLE_OPERATOR.name(), ROLE_MEMBER.name());
    }

    private void businessAccountsAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/business/**").authenticated();
    }

    private void adAccountsAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adaccounts/**").authenticated();
    }

    private void campaignAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/campaigns/search/**", "/ad-type-goal").authenticated()
                .antMatchers(HttpMethod.POST, "/campaigns/**")
                .hasAnyAuthority(ROLE_ADMIN.name(), ROLE_OPERATOR.name(), ROLE_MEMBER.name());
    }

    private void adGroupAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adgroups/search/**").authenticated();
    }

    private void creativeAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/creatives/search/**").authenticated()
                .antMatchers("/creatives")
                .hasAnyAuthority(ROLE_ADMIN.name(), ROLE_OPERATOR.name(), ROLE_MEMBER.name());
    }

    private void mediaAndDeviceAntMatchers(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/media", "/device").authenticated();
    }

    private void permitAll(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/exception/**", "/", "/files/**","/batch/**").permitAll()
                // 추가
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight Request 허용해주기
                .anyRequest().authenticated();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:3001");
        configuration.addAllowedOrigin("http://localhost:3002");
        configuration.addAllowedOrigin("http://advertiser.union-mobile.co.kr");
        configuration.addAllowedOrigin("http://ec2-13-209-158-233.ap-northeast-2.compute.amazonaws.com");
//        configuration.addAllowedOrigin("http://*:8083");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
