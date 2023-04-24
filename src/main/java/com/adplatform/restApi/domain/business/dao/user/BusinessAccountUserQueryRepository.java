package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface BusinessAccountUserQueryRepository {
    Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);
    Integer findByBusinessAccountIdAndUserIdCount(Integer businessAccountId, Integer userId);
    Integer findByBusinessAccountIdCount(Integer businessAccountId);
    Page<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserInfo(Integer businessAccountId, Pageable pageable);
    Page<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserSearch(Integer businessAccountId, BusinessAccountDto.Request.SearchUser searchRequest, Pageable pageable);
    List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountRequestUserInfo(Integer businessAccountId);
    List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountMasterUserInfo(Integer businessAccountId);
    BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo(Integer businessAccountId, Integer userNo);

    void deleteByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);
    void updateAccounting(Integer businessAccountId, Integer userId, BusinessAccountUser.AccountingYN accountingYN);
    List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId);

}
