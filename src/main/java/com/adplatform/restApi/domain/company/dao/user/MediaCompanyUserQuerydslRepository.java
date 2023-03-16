package com.adplatform.restApi.domain.company.dao.user;


import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;

import java.util.List;
import java.util.Optional;

public interface MediaCompanyUserQuerydslRepository {

    Optional<MediaCompanyUser> findByCompanyIdAndUserId(Integer companyId, Integer userId);
    Integer findByCompanyIdAndUserIdCount(Integer companyId, Integer userId);
    Integer findByCompanyIdCount(Integer companyId);
    List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyUserInfo(Integer companyId);
    List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyRequestUserInfo(Integer companyId);
    MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo(Integer companyId, Integer userNo);
    void deleteByCompanyIdAndUserIdCount(Integer companyId, Integer userId);

    void updateAccounting(Integer companyId, Integer userId, MediaCompanyUser.AccountingYN accountingYN);
}