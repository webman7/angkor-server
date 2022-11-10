package com.adplatform.restApi.global.config.security.util;

import com.adplatform.restApi.global.config.security.dto.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Integer getLoginUserId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getUser().getId();
    }

    public static String getLoginUserLoginId() {
        Authentication authentication = validateAnonymousAuthentication();
        if (authentication == null) return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getUser().getLoginId();
    }

    private static Authentication validateAnonymousAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        return authentication;
    }
}
