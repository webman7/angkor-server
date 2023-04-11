package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface AdAccountUserQueryRepository {
    Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId);

    Integer findByAdAccountIdAndUserIdCount(Integer adAccountId, Integer userId);
    Page<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserInfo(Integer adAccountId, Pageable pageable);
    Page<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserSearch(Integer adAccountId, AdAccountDto.Request.SearchUser searchRequest, Pageable pageable);

    List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountRequestUserInfo(Integer adAccountId);
    AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo(Integer adAccountId, Integer userNo);

    void deleteByAdAccountIdAndUserId(Integer adAccountId, Integer userId);
}
