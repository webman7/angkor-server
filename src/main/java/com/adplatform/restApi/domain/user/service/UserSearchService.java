package com.adplatform.restApi.domain.user.service;

import com.adplatform.restApi.domain.user.dao.RoleRepository;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.Role;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.exception.RoleNotFoundException;
import com.adplatform.restApi.domain.user.exception.UserAlreadyExistException;
import com.adplatform.restApi.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserSearchService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void validateExistsByLoginId(AuthDto.Request.SignUp request) {
        if (this.userRepository.existsByLoginId(request.getId()))
            throw new UserAlreadyExistException();
    }

    public User findByIdOrElseThrow(String id) {
        return this.userRepository.findByLoginId(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public Role findRoleByType(Role.Type type) {
        return this.roleRepository.findByValue(type)
                .orElseThrow(RoleNotFoundException::new);
    }

    public User findByLoginIdAndName(String loginId, String name) {
        return this.userRepository.findByLoginIdAndName(loginId, name)
                .orElseThrow(UserNotFoundException::new);
    }
}
