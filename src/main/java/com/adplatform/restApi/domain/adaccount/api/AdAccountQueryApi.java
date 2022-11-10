package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("adaccounts")
public class AdAccountQueryApi {
    private final AdAccountRepository adAccountRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<AdAccountDto.Response.Page> mySearch(
            @PageableDefault Pageable pageable,
            AdAccountDto.Request.MySearch request) {
        return PageDto.create(this.adAccountRepository.search(pageable, request));
    }
}
