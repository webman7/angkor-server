package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface AdAccountUserQueryRepository {
    Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId);

    Integer findByAdAccountIdAndUserIdCount(Integer adAccountId, Integer userId);
    List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserInfo(Integer adAccountId);
    List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountRequestUserInfo(Integer adAccountId);
    AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo(Integer adAccountId, Integer userNo);
    void deleteByAdAccountIdAndUserIdCount(Integer adAccountId, Integer userId);
}
