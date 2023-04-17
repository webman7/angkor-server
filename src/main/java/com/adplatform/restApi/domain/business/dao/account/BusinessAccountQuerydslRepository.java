package com.adplatform.restApi.domain.business.dao.account;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.advertiser.dashboard.dto.DashboardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface BusinessAccountQuerydslRepository {

    Page<BusinessAccountDto.Response.ForAdminSearch> searchForAdmin(Pageable pageable, BusinessAccountDto.Request.ForAdminSearch request);

    List<BusinessAccountDto.Response.ForAdminSearch> searchForAdmin(BusinessAccountDto.Request.ForAdminSearch request);

    Page<BusinessAccountDto.Response.ForAgencySearch> searchForAgency(Pageable pageable, BusinessAccountDto.Request.ForAgencySearch request, Integer userId);

    List<BusinessAccountDto.Response.ForAgencySearch> searchForAgency(BusinessAccountDto.Request.ForAgencySearch request, Integer userId);

    Page<BusinessAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserNo, BusinessAccountUser.Status requestStatus);

    List<BusinessAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            Integer id, String name, Integer loginUserNo, BusinessAccountUser.Status requestStatus);
    Page<BusinessAccountDto.Response.ForCashSearch> searchForCash(Pageable pageable, BusinessAccountDto.Request.ForCashSearch request);
    List<BusinessAccountDto.Response.ForCashSearch> searchForCash(BusinessAccountDto.Request.ForCashSearch request);
    Optional<BusinessAccountDto.Response.BusinessAccountCount> countStatusYN(Integer loginUserNo);
    BusinessAccountDto.Response.BusinessAccountInfo businessAccountInfo(Integer businessAccountId);
    List<BusinessAccountDto.Response.AdAccountMemberInfo> businessAccountByAdAccounts(Integer businessAccountId);
    List<BusinessAccountDto.Response.AdAccountMemberInfo> businessAccountByAdAccountsMember(Integer businessAccountId, Integer loginUserNo);
    Page<BusinessAccountDto.Response.BusinessAccountCreditInfo> searchCredit(Pageable pageable, BusinessAccountDto.Request.SearchCredit searchRequest);
    Page<BusinessAccountDto.Response.BusinessAccountSearch> searchBusiness(Pageable pageable, BusinessAccountDto.Request.SearchBusiness searchRequest);

    BusinessAccountDto.Response.BusinessAccountCreditInfo businessAccountCreditInfo(Integer businessAccountId);
    Page<BusinessAccountDto.Response.BusinessAccountTaxInfo> searchTax(Pageable pageable, BusinessAccountDto.Request.SearchTax searchRequest);
    void outOfBalanceUpdate(Integer businessAccountId, Boolean oufOfBalance);


//    DashboardDto.Response.BusinessAccountCountByAd businessAccountsCountByAd(Integer businessAccountId);
//

}
