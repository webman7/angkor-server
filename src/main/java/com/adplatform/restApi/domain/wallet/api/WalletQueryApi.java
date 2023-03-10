package com.adplatform.restApi.domain.wallet.api;

import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
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

    final private WalletLogRepository walletLogRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/cash/search")
    public PageDto<WalletDto.Response.CashSearch> searchForFreeCash(
            @PageableDefault Pageable pageable,
            WalletDto.Request.CashSearch request) {
        return PageDto.create(this.walletLogRepository.searchForCash(pageable, request));
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/freecash/search")
//    public PageDto<WalletDto.Response.FreeCashSearch> searchForFreeCash(
//            @PageableDefault Pageable pageable,
//            WalletDto.Request.FreeCashSearch request) {
//        System.out.println("==============================================");
//        System.out.println(request.getAdAccountId());
//        return PageDto.create(this.walletFreeCashRepository.searchForFreeCash(pageable, request));
//    }
}
