package com.adplatform.restApi.domain.report.api;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
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
@RequestMapping("/report")
public class ReportQueryApi {

    private final ReportCustomRepository reportCustomRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/custom/search")
    public PageDto<ReportCustomDto.Response.Default> search(
            @PageableDefault Pageable pageable,
            ReportCustomDto.Request.Search request) {
        return PageDto.create(this.reportCustomRepository.search(pageable, request));
    }
}
