package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface BusinessAccountUserQueryRepository {
    Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);
    Integer findByBusinessAccountIdAndUserIdCount(Integer businessAccountId, Integer userId);
    List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserInfo(Integer businessAccountId);
    List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountRequestUserInfo(Integer businessAccountId);
    BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo(Integer businessAccountId, Integer userNo);
    void deleteByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);
    void updateAccounting(Integer businessAccountId, Integer userId, BusinessAccountUser.AccountingYN accountingYN);
    List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);

}
