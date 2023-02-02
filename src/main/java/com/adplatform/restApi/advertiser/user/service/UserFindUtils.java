package com.adplatform.restApi.advertiser.user.service;

import com.adplatform.restApi.advertiser.user.dao.UserRepository;
import com.adplatform.restApi.advertiser.user.domain.User;
import com.adplatform.restApi.advertiser.user.exception.UserNotFoundException;

public class UserFindUtils {

    public static User findByIdOrElseThrow(Integer id, UserRepository repository) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
