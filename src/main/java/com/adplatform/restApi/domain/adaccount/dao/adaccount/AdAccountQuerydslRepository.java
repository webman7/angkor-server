package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdAccountQuerydslRepository {
    Page<AdAccountDto.Response.ForAgencySearch> searchForAgency(Pageable pageable, AdAccountDto.Request.ForAgencySearch request, Integer userId);

    List<AdAccountDto.Response.ForAgencySearch> searchForAgency(AdAccountDto.Request.ForAgencySearch request, Integer userId);

    Page<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus);

    Optional<AdAccountDto.Response.AdAccountCount> countRequestStatusYN(Integer loginUserId);

    AdAccountDto.Response.AdAccountInfo adAccountInfo(Integer adAccountId);
}
