package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("adaccounts")
public class AdAccountQueryApi {
    private final AdAccountRepository adAccountRepository;

    @GetMapping("/mySearch")
    public ResponseEntity<PageDto<AdAccountDto.Response.Page>> mySearch(
            @PageableDefault Pageable pageable,
            AdAccountDto.Request.MySearch searchRequest) {
        return ResponseEntity.ok(PageDto.create(this.adAccountRepository.mySearch(pageable, searchRequest)));
    }
}
