package com.adplatform.restApi.domain.business.api;

import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.dao.account.mapper.BusinessAccountQueryMapper;
import com.adplatform.restApi.domain.business.dao.user.BusinessAccountUserRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessAccountQueryApi {
    private final BusinessAccountRepository businessAccountRepository;
    private final BusinessAccountUserRepository businessAccountUserRepository;
    private final BusinessAccountQueryMapper businessAccountQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/accounts")
    public PageDto<BusinessAccountDto.Response.Accounts> accounts(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name) {

        return PageDto.create(new PageImpl<>(
                this.businessAccountQueryMapper.accounts(pageable, id, name, SecurityUtils.getLoginUserNo()),
                pageable,
                this.businessAccountQueryMapper.countAccounts(id, name, SecurityUtils.getLoginUserNo())));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/counts")
    public BusinessAccountDto.Response.BusinessAccountCount getCounts() {
        return this.businessAccountRepository.countStatusYN(SecurityUtils.getLoginUserNo())
                .orElse(new BusinessAccountDto.Response.BusinessAccountCount(0L, 0L));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/cash")
    public BusinessAccountDto.Response.BusinessAccountCashInfo businessAccountCashInfo(@PathVariable(name = "id") Integer businessAccountId) {
        return this.businessAccountRepository.businessAccountCashInfo(businessAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}")
    public BusinessAccountDto.Response.BusinessAccountInfo businessAccountInfo(@PathVariable(name = "id") Integer businessAccountId) {
        return this.businessAccountRepository.businessAccountInfo(businessAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/users")
    public PageDto<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserInfo(
            @PathVariable(name = "id") Integer businessAccountId,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.businessAccountUserRepository.businessAccountUserInfo(businessAccountId, pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/users/request")
    public List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountRequestUserInfo(@PathVariable(name = "id") Integer businessAccountId) {
        return this.businessAccountUserRepository.businessAccountRequestUserInfo(businessAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/user/{userNo}")
    public BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo(@PathVariable(name = "id") Integer businessAccountId, @PathVariable(name = "userNo") Integer userNo) {
        return this.businessAccountUserRepository.businessAccountUserInfo(businessAccountId, userNo);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/adaccounts/master")
    public List<BusinessAccountDto.Response.AdAccountInfo> businessAccountByAdAccounts(@PathVariable(name = "id") Integer businessAccountId) {
        return this.businessAccountRepository.businessAccountByAdAccounts(businessAccountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/account/{id}/adaccounts/member")
    public List<BusinessAccountDto.Response.AdAccountMemberInfo> businessAccountByAdAccountsMember(@PathVariable(name = "id") Integer businessAccountId) {
        return this.businessAccountRepository.businessAccountByAdAccountsMember(businessAccountId, SecurityUtils.getLoginUserNo());
    }







    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<BusinessAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam BusinessAccountUser.Status status) {
        return this.businessAccountRepository.searchForAdvertiser(
                id, name, SecurityUtils.getLoginUserNo(), status);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-admin")
    public PageDto<BusinessAccountDto.Response.ForAdminSearch> searchForAdmin(
            @PageableDefault Pageable pageable,
            BusinessAccountDto.Request.ForAdminSearch request) {
        return PageDto.create(this.businessAccountRepository.searchForAdmin(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-agency")
    public PageDto<BusinessAccountDto.Response.ForAgencySearch> searchForAgency(
            @PageableDefault Pageable pageable,
            BusinessAccountDto.Request.ForAgencySearch request) {
        return PageDto.create(this.businessAccountRepository.searchForAgency(pageable, request, SecurityUtils.getLoginUserNo()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-advertiser")
    public PageDto<BusinessAccountDto.Response.ForAdvertiserSearch> searchForAdvertiser(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam BusinessAccountUser.Status status) {
        return PageDto.create(this.businessAccountRepository.searchForAdvertiser(
                pageable, id, name, SecurityUtils.getLoginUserNo(), status));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-cash")
    public PageDto<BusinessAccountDto.Response.ForCashSearch> searchForCash(
            @PageableDefault Pageable pageable,
            BusinessAccountDto.Request.ForCashSearch request) {
        return PageDto.create(this.businessAccountRepository.searchForCash(pageable, request));
    }
}
