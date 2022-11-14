package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("adaccounts")
public class AdAccountQueryApi {
    private final AdAccountRepository adAccountRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-agency")
    public PageDto<AdAccountDto.Response.Page> searchForAgency(
            @PageableDefault Pageable pageable,
            AdAccountDto.Request.MySearch request) {
        return PageDto.create(this.adAccountRepository.search(pageable, request, SecurityUtils.getLoginUserId()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search-for-advertiser")
    public PageDto<AdAccountDto.Response.ForAdvertiser> searchForAdvertiser(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam AdAccountUser.RequestStatus requestStatus) {
        return PageDto.create(this.adAccountRepository.searchForAdvertiser(
                pageable, id, name, SecurityUtils.getLoginUserId(), requestStatus));
    }
}
