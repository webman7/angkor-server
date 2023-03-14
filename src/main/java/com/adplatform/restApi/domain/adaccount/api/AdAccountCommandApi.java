package com.adplatform.restApi.domain.adaccount.api;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.adaccount.service.AdAccountSaveService;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junny
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

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@RequestBody @Valid AdAccountDto.Request.Update request) {
        this.adAccountSaveService.update(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/invite")
    public void saveUserInvite(@RequestBody @Valid AdAccountUserDto.Request.SaveUser request) {
        this.adAccountSaveService.saveUserInvite(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/member")
    public void updateUserMember(@RequestBody @Valid AdAccountUserDto.Request.UserMemberUpdate request) {
        this.adAccountSaveService.updateUserMember(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/status")
    public void updateUserStatus(@RequestBody @Valid AdAccountUserDto.Request.UserStatusUpdate request) {
        this.adAccountSaveService.updateUserStatus(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user")
    public void deleteUser(@RequestBody @Valid AdAccountUserDto.Request.UserUpdate request) {
        this.adAccountSaveService.deleteUser(request, SecurityUtils.getLoginUserNo());
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

//    @AuthorizedAdAccount
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("/credit-limit")
//    public void creditLimitUpdate(@RequestBody @Valid AdAccountDto.Request.CreditLimitUpdate request) {
//        this.adAccountSaveService.creditLimitUpdate(request);
//    }

}
