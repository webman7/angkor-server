package com.adplatform.restApi.domain.business.api;

import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.business.service.BusinessAccountSaveService;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/business")
public class BusinessAccountCommandApi {
    private final BusinessAccountSaveService businessAccountSaveService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody @Valid BusinessAccountDto.Request.Save request) {
        this.businessAccountSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public void update(@RequestBody @Valid BusinessAccountDto.Request.Update request) {
        this.businessAccountSaveService.update(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("admin")
    public void saveAdmin(@RequestBody @Valid BusinessAccountDto.Request.Save request) {
        this.businessAccountSaveService.saveAdmin(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("admin")
    public void updateAdmin(@RequestBody @Valid BusinessAccountDto.Request.Update request) {
        this.businessAccountSaveService.updateAdmin(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/invite")
    public void saveUserInvite(@RequestBody @Valid BusinessAccountUserDto.Request.SaveUser request) {
        this.businessAccountSaveService.saveUserInvite(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/member")
    public void updateUserMember(@RequestBody @Valid BusinessAccountUserDto.Request.UserMemberUpdate request) {
        this.businessAccountSaveService.updateUserMember(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/status")
    public void updateUserStatus(@RequestBody @Valid BusinessAccountUserDto.Request.UserStatusUpdate request) {
        this.businessAccountSaveService.updateUserStatus(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/accounting")
    public void updateUserAccounting(@RequestBody @Valid BusinessAccountUserDto.Request.UserUpdate request) {
        this.businessAccountSaveService.updateUserAccounting(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("user/admin")
    public void saveUserAdmin(@RequestBody @Valid BusinessAccountUserDto.Request.SaveUser request) {
        this.businessAccountSaveService.saveUserAdmin(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("user/admin/accounting")
    public void updateUserAdminAccounting(@RequestBody @Valid BusinessAccountUserDto.Request.UserUpdate request) {
        this.businessAccountSaveService.updateUserAdminAccounting(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("user")
    public void deleteUser(@RequestBody @Valid BusinessAccountUserDto.Request.UserUpdate request) {
        this.businessAccountSaveService.deleteUser(request, SecurityUtils.getLoginUserNo());
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

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/refund/account")
    public void update(@RequestBody @Valid BusinessAccountDto.Request.UpdateRefundAccount request) {
        this.businessAccountSaveService.updateRefundAccount(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/tax/bill/issue")
    public void updateTaxIssue(@Valid TaxBillDto.Request.BusinessTaxBillUpdate request) {
        this.businessAccountSaveService.updateTaxIssue(request, SecurityUtils.getLoginUserNo());
    }
}
