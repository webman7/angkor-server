package com.adplatform.restApi.domain.agency.adaccount.api;

import com.adplatform.restApi.domain.agency.adaccount.dao.mapper.AgencyAdAccountQueryMapper;
import com.adplatform.restApi.domain.agency.adaccount.dto.AgencyAdAccountDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agency/adaccounts")
public class AgencyAdAccountQueryApi {

    private final AgencyAdAccountQueryMapper agencyAdAccountQueryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/businessright/search")
    public PageDto<AgencyAdAccountDto.Response.Search> search(
            @PageableDefault Pageable pageable,
            AgencyAdAccountDto.Request.Search request) {

        return PageDto.create(new PageImpl<>(
                this.agencyAdAccountQueryMapper.search(request, pageable),
                pageable,
                this.agencyAdAccountQueryMapper.countSearch(request)));
    }
}
