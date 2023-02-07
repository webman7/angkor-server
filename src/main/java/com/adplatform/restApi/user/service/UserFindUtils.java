package com.adplatform.restApi.user.service;

import com.adplatform.restApi.user.dao.UserRepository;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.user.exception.UserNotFoundException;

public class UserFindUtils {

    public static User findByIdOrElseThrow(Integer id, UserRepository repository) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
