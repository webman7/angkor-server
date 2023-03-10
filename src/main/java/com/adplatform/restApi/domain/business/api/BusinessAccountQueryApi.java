package com.adplatform.restApi.domain.business.api;

import com.adplatform.restApi.domain.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.dao.account.mapper.BusinessAccountQueryMapper;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessAccountQueryApi {
    private final BusinessAccountRepository businessAccountRepository;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/counts")
    public BusinessAccountDto.Response.BusinessAccountCount getCounts() {
        return this.businessAccountRepository.countStatusYN(SecurityUtils.getLoginUserNo())
                .orElse(new BusinessAccountDto.Response.BusinessAccountCount(0L, 0L));
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}")
//    public BusinessAccountDto.Response.BusinessAccountInfo businessAccountInfo(@PathVariable(name = "id") Integer businessAccountId) {
//        return this.businessAccountRepository.businessAccountInfo(businessAccountId);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/cash")
//    public BusinessAccountDto.Response.BusinessAccountCashInfo businessAccountCashInfo(@PathVariable(name = "id") Integer businessAccountId) {
//        return this.businessAccountRepository.businessAccountCashInfo(businessAccountId);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/cash/detail")
//    public List<BusinessAccountDto.Response.BusinessAccountCashDetailInfo> businessAccountCashDetailInfo(@PathVariable(name = "id") Integer businessAccountId) {
//        return this.businessAccountRepository.businessAccountCashDetailInfo(businessAccountId);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}/advertiser")
//    public CompanyDto.Response.BusinessAccountDetail businessAccountByAdvertiser(@PathVariable(name = "id") Integer businessAccountId) {
//        return this.businessAccountRepository.businessAccountByAdvertiser(businessAccountId);
//    }
}
