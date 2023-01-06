package com.adplatform.restApi.domain.report.api;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.domain.report.service.ReportSaveService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportCommandApi {

    private final ReportSaveService reportSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/custom")
    public void save(@RequestBody @Valid ReportCustomDto.Request.Save request) {
        this.reportSaveService.save(request, SecurityUtils.getLoginUserId());
    }
}
