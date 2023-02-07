package com.adplatform.restApi.domain.wallet.api;

import com.adplatform.restApi.domain.wallet.service.WalletSaveService;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletCommandApi {

    private final WalletSaveService walletSaveService;
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/cash/add")
    public void save(@RequestBody @Valid WalletDto.Request.SaveCash request) {
        this.walletSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/freecash/add")
    public void saveFreeCash(@RequestBody @Valid WalletDto.Request.SaveFreeCash request) {
        this.walletSaveService.saveFreeCash(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freecash/{id}/use")
    public void updateFreeCashUse(@PathVariable(name = "id") Integer id) {
        this.walletSaveService.updateFreeCashStatus(id, "USED", SecurityUtils.getLoginUserNo());
    }
}
