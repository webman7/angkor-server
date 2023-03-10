package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.advertiser.dashboard.dto.DashboardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdAccountQuerydslRepository {
    Page<AdAccountDto.Response.ForAdminSearch> searchForAdmin(Pageable pageable, AdAccountDto.Request.ForAdminSearch request);

    List<AdAccountDto.Response.ForAdminSearch> searchForAdmin(AdAccountDto.Request.ForAdminSearch request);

    Page<AdAccountDto.Response.ForAgencySearch> searchForAgency(Pageable pageable, AdAccountDto.Request.ForAgencySearch request, Integer userId);

    List<AdAccountDto.Response.ForAgencySearch> searchForAgency(AdAccountDto.Request.ForAgencySearch request, Integer userId);

    Page<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserNo, AdAccountUser.Status status);

    List<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Integer id, String name, Integer loginUserNo, AdAccountUser.Status status);

    Page<AdAccountDto.Response.ForCashSearch> searchForCash(Pageable pageable, AdAccountDto.Request.ForCashSearch request);

    List<AdAccountDto.Response.ForCashSearch> searchForCash(AdAccountDto.Request.ForCashSearch request);

    Optional<AdAccountDto.Response.AdAccountCount> countStatusYN(Integer loginUserNo);

    AdAccountDto.Response.AdAccountInfo adAccountInfo(Integer adAccountId);

//    AdAccountDto.Response.AdAccountCashInfo adAccountCashInfo(Integer adAccountId);

//    List<AdAccountDto.Response.AdAccountCashDetailInfo> adAccountCashDetailInfo(Integer adAccountId);

    void outOfBalanceUpdate(Integer adAccountId, Boolean oufOfBalance);

    DashboardDto.Response.AdAccountCountByAd adAccountsCountByAd(Integer adAccountId);

    CompanyDto.Response.AdAccountDetail adAccountByAdvertiser(Integer adAccountId);

}
