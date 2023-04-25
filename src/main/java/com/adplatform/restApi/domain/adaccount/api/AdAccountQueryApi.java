package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adaccounts")
public class AdAccountQueryApi {
    private final AdAccountRepository adAccountRepository;
    private final AdAccountUserRepository adAccountUserRepository;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public AdAccountDto.Response.AdAccountInfo adAccountInfo(@PathVariable(name = "id") Integer adAccountId) {
        return this.adAccountRepository.adAccountInfo(adAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users")
    public PageDto<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserInfo(
            @PathVariable(name = "id") Integer adAccountId,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adAccountUserRepository.adAccountUserInfo(adAccountId, pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users/search")
    public PageDto<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserSearch(
            @PathVariable(name = "id") Integer adAccountId,
            AdAccountDto.Request.SearchUser searchRequest,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.adAccountUserRepository.adAccountUserSearch(adAccountId, searchRequest, pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/users/request")
    public List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountRequestUserInfo(@PathVariable(name = "id") Integer adAccountId) {
        return this.adAccountUserRepository.adAccountRequestUserInfo(adAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/user/{userNo}")
    public AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo(@PathVariable(name = "id") Integer adAccountId, @PathVariable(name = "userNo") Integer userNo) {
        return this.adAccountUserRepository.adAccountUserInfo(adAccountId, userNo);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<AdAccountDto.Response.AdAccountSearch> searchAdAccount(
            @PageableDefault Pageable pageable,
            AdAccountDto.Request.SearchAdAccount searchRequest) {
        return PageDto.create(this.adAccountRepository.searchAdAccount(pageable, searchRequest));
    }














    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam AdAccountUser.Status status) {
        return this.adAccountRepository.searchForAdvertiser(
                id, name, SecurityUtils.getLoginUserNo(), status);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search-for-admin")
//    public PageDto<AdAccountDto.Response.ForAdminSearch> searchForAdmin(
//            @PageableDefault Pageable pageable,
//            AdAccountDto.Request.ForAdminSearch request) {
//        return PageDto.create(this.adAccountRepository.searchForAdmin(pageable, request));
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search-for-agency")
//    public PageDto<AdAccountDto.Response.ForAgencySearch> searchForAgency(
//            @PageableDefault Pageable pageable,
//            AdAccountDto.Request.ForAgencySearch request) {
//        return PageDto.create(this.adAccountRepository.searchForAgency(pageable, request, SecurityUtils.getLoginUserNo()));
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-advertiser")
    public PageDto<AdAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam AdAccountUser.Status status) {
        return PageDto.create(this.adAccountRepository.searchForAdvertiser(
                pageable, id, name, SecurityUtils.getLoginUserNo(), status));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-cash")
    public PageDto<AdAccountDto.Response.ForCashSearch> searchForCash(
            @PageableDefault Pageable pageable,
            AdAccountDto.Request.ForCashSearch request) {
        return PageDto.create(this.adAccountRepository.searchForCash(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/counts")
    public AdAccountDto.Response.AdAccountCount getCounts() {
        return this.adAccountRepository.countStatusYN(SecurityUtils.getLoginUserNo())
                .orElse(new AdAccountDto.Response.AdAccountCount(0L, 0L));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/advertiser")
    public CompanyDto.Response.AdAccountDetail adAccountByAdvertiser(@PathVariable(name = "id") Integer adAccountId) {
        return this.adAccountRepository.adAccountByAdvertiser(adAccountId);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/cash")
//    public AdAccountDto.Response.AdAccountCashInfo adAccountCashInfo(@PathVariable(name = "id") Integer adAccountId) {
//        return this.adAccountRepository.adAccountCashInfo(adAccountId);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/cash/detail")
//    public List<AdAccountDto.Response.AdAccountCashDetailInfo> adAccountCashDetailInfo(@PathVariable(name = "id") Integer adAccountId) {
//        return this.adAccountRepository.adAccountCashDetailInfo(adAccountId);
//    }

}
