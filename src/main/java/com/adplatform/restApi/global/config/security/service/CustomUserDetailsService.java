package com.adplatform.restApi.global.config.security.service;

import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.exception.UserNotFoundException;
import com.adplatform.restApi.global.config.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return new CustomUserDetails(this.userRepository.findByLoginId(userId)
                .orElseThrow(UserNotFoundException::new));
    }
}
