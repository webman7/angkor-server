package com.adplatform.restApi.domain.user.service;

import com.adplatform.restApi.domain.history.dao.user.UserPasswordChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.domain.user.dao.RoleRepository;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.Role;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.exception.RoleNotFoundException;
import com.adplatform.restApi.domain.user.exception.UserAlreadyExistException;
import com.adplatform.restApi.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private final UserPasswordChangeHistoryRepository userPasswordChangeHistoryRepository;

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
    public UserDto.Response.BaseInfo findUserByLoginIdAndName(String loginId, String name) {
        return this.userRepository.findUserByLoginIdAndName(loginId, name);
    }

    public UserPasswordChangeHistory findPasswordCert(AuthDto.Request.FindPasswordCert request) {
        return this.userRepository.findPasswordCert(request)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserPasswordChangeHistory findPasswordChange(AuthDto.Request.FindPasswordChange request) {
        return this.userPasswordChangeHistoryRepository.findByIdAndStatus(Integer.parseInt(request.getHistoryId()), UserPasswordChangeHistory.Status.WAITING)
                .orElseThrow(UserNotFoundException::new);
    }
}
