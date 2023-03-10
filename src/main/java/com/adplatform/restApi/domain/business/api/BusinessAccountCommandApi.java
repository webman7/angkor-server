package com.adplatform.restApi.domain.business.api;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.service.BusinessAccountSaveService;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
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
@RequestMapping("/businessAccounts")
public class BusinessAccountCommandApi {
    private final BusinessAccountSaveService businessAccountSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid BusinessAccountDto.Request.Save request) {
        this.businessAccountSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @AuthorizedAdAccount
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/credit-limit")
    public void creditLimitUpdate(@RequestBody @Valid BusinessAccountDto.Request.CreditLimitUpdate request) {
        this.businessAccountSaveService.creditLimitUpdate(request);
    }
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/on")
    public void changeConfigOn(@PathVariable Integer id) {
        this.businessAccountSaveService.changeConfig(id, BusinessAccount.Config.ON);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/config/off")
    public void changeConfigOff(@PathVariable Integer id) {
        this.businessAccountSaveService.changeConfig(id, BusinessAccount.Config.OFF);
    }
}
