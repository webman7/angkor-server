package com.adplatform.restApi.user.service;

import com.adplatform.restApi.user.dao.RoleRepository;
import com.adplatform.restApi.user.dao.UserRepository;
import com.adplatform.restApi.user.domain.Role;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.user.dto.auth.AuthDto;
import com.adplatform.restApi.user.exception.RoleNotFoundException;
import com.adplatform.restApi.user.exception.UserAlreadyExistException;
import com.adplatform.restApi.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class UserQueryService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User findByIdOrElseThrow(Integer id) {
        return this.userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void validateExistsByLoginId(AuthDto.Request.SignUp request) {
        if (this.userRepository.existsByLoginId(request.getId()))
            throw new UserAlreadyExistException();
    }

    public User findByLoginIdOrElseThrow(String id) {
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
