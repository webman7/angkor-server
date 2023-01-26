package com.adplatform.restApi.domain.user.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.exception.CompanyNotFoundException;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.exception.UserNotFoundException;

public class UserFindUtils {

    public static User findByIdOrElseThrow(Integer id, UserRepository repository) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
