package com.adplatform.restApi.domain.wallet.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.wallet.dao.walletcashtotal.WalletCashTotalRepository;
import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.domain.Cash;
import com.adplatform.restApi.domain.wallet.domain.WalletCashTotal;
import com.adplatform.restApi.domain.wallet.domain.WalletLog;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.adplatform.restApi.domain.wallet.dto.WalletLogMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class WalletSaveService {
    private final AdAccountRepository adAccountRepository;
    private final WalletLogRepository walletLogRepository;

    private final WalletCashTotalRepository walletCashTotalRepository;

    private final WalletLogMapper walletLogMapper;

    @Data
    @AllArgsConstructor
    static class WalletCashDto{ // 노출할 것만
        private Long amount;
        private Long availableAmount;
    }

    public void save(WalletDto.Request.Save request, Integer loginUserId) {
        Integer newTradeNo = walletLogRepository.getNewTradeNo(request.getId());
        WalletDto.Response.WalletCashTotal list = walletLogRepository.getCashTotalByCashId(request.getId(), request.getCashId());
        // 총합캐시
        Long prevAmount = list.getAmount();
        // 가용캐시
        Long prevAvailableAmount = list.getAvailableAmount();

        // 잔액 = 가용캐시 + 입금
        Long balance = prevAvailableAmount + request.getInAmount();

        // total 캐시 변경
        Long amount = prevAmount + request.getInAmount();
        Long availableAmount = prevAvailableAmount + request.getInAmount();

        request.setTradeNo(newTradeNo + 1);
        request.setBalance(balance);

        this.adAccountRepository.creditLimitUpdate(request.getId(), false);
        WalletLog walletLog = this.walletLogMapper.toEntity(request, loginUserId);
        this.walletCashTotalRepository.updateWalletCashAdd(request.getId(), request.getCashId(), amount, availableAmount);
        this.walletLogRepository.save(walletLog);
    }
}
