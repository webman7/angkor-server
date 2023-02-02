package com.adplatform.restApi.advertiser.wallet.api;

import com.adplatform.restApi.advertiser.wallet.dao.walletfreecash.WalletFreeCashRepository;
import com.adplatform.restApi.advertiser.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.advertiser.wallet.dto.WalletDto;
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

    final private WalletLogRepository walletLogRepository;
    final private WalletFreeCashRepository walletFreeCashRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/cash/search")
    public PageDto<WalletDto.Response.CashSearch> searchForFreeCash(
            @PageableDefault Pageable pageable,
            WalletDto.Request.CashSearch request) {
        return PageDto.create(this.walletLogRepository.searchForCash(pageable, request));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/freecash/search")
    public PageDto<WalletDto.Response.FreeCashSearch> searchForFreeCash(
            @PageableDefault Pageable pageable,
            WalletDto.Request.FreeCashSearch request) {
        System.out.println("==============================================");
        System.out.println(request.getAdAccountId());
        return PageDto.create(this.walletFreeCashRepository.searchForFreeCash(pageable, request));
    }
}
