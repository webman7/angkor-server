package com.adplatform.restApi.domain.wallet.api;

import com.adplatform.restApi.domain.wallet.dao.walletchargelog.WalletChargeLogRepository;
import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.dao.walletrefund.WalletRefundRepository;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletQueryApi {

    private final WalletLogRepository walletLogRepository;
    private final WalletRefundRepository walletRefundRepository;
    private final WalletChargeLogRepository walletChargeLogRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/credit/log/search")
    public PageDto<WalletDto.Response.CreditSearch> searchForCreditLog(
            @PageableDefault Pageable pageable,
            WalletDto.Request.CreditSearch request) {
        return PageDto.create(this.walletLogRepository.searchForCreditLog(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/charge/search")
    public PageDto<WalletDto.Response.ChargeSearch> searchForRefund(
            @PageableDefault Pageable pageable,
            WalletDto.Request.ChargeSearch request) {
        return PageDto.create(this.walletChargeLogRepository.searchForCharge(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/refund/search")
    public PageDto<WalletDto.Response.RefundSearch> searchForRefund(
            @PageableDefault Pageable pageable,
            WalletDto.Request.RefundSearch request) {
        return PageDto.create(this.walletRefundRepository.searchForRefund(pageable, request));
    }
}
