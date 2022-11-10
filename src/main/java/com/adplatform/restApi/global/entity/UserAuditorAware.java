package com.adplatform.restApi.global.entity;

import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class UserAuditorAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.getLoginUserId());
    }
}
