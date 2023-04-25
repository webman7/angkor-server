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
    @PostMapping("/credit/add")
    public void save(@Valid WalletDto.Request.SaveCredit request) {
        this.walletSaveService.save(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refund/request")
    public void save(@RequestBody @Valid WalletDto.Request.SaveRefund request) {
        this.walletSaveService.saveRefund(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/refund/approve")
    public void updateApprove(WalletDto.Request.UpdateRefund request) {
        this.walletSaveService.updateRefundApprove(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/refund/reject")
    public void updateReject(WalletDto.Request.UpdateRefund request) {
        this.walletSaveService.updateRefundReject(request, SecurityUtils.getLoginUserNo());
    }

}
