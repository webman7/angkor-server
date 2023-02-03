package com.adplatform.restApi.advertiser.adaccount.api;

import com.adplatform.restApi.advertiser.adaccount.domain.AdAccount;
import com.adplatform.restApi.advertiser.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.advertiser.adaccount.service.AdAccountSaveService;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/adaccounts")
public class AdAccountCommandApi {
    private final AdAccountSaveService adAccountSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid AdAccountDto.Request.Save request) {
        this.adAccountSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/credit-limit")
    public void creditLimitUpdate(@RequestBody @Valid AdAccountDto.Request.CreditLimitUpdate request) {
        this.adAccountSaveService.creditLimitUpdate(request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/on")
    public void changeConfigOn(@PathVariable Integer id) {
        this.adAccountSaveService.changeConfig(id, AdAccount.Config.ON);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/off")
    public void changeConfigOff(@PathVariable Integer id) {
        this.adAccountSaveService.changeConfig(id, AdAccount.Config.OFF);
    }
}
