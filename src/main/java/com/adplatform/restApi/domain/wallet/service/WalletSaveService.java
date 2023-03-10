package com.adplatform.restApi.domain.wallet.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletLog;
import com.adplatform.restApi.domain.wallet.dao.walletlog.WalletLogRepository;
import com.adplatform.restApi.domain.wallet.dto.WalletLogMapper;
import com.adplatform.restApi.domain.wallet.exception.CashNotFoundException;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WalletSaveService {
    private final AdAccountRepository adAccountRepository;
    private final WalletLogRepository walletLogRepository;
    private final WalletLogMapper walletLogMapper;

    @Data
    @AllArgsConstructor
    static class WalletCashDto{ // 노출할 것만
        private Long amount;
        private Long availableAmount;
    }

    public void save(WalletDto.Request.SaveCash request, Integer loginUserNo) {
        this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);

//        WalletDto.Response.WalletCashTotal list = walletCashTotalRepository.getCashTotalByCashId(request.getAdAccountId(), request.getCashId());
//        // 총합캐시
//        Long prevAmount = list.getAmount();
//        // 가용캐시
//        Long prevAvailableAmount = list.getAvailableAmount();
//
//        // total 캐시 변경
//        Long amount = prevAmount + request.getPubAmount();
//        Long availableAmount = prevAvailableAmount + request.getPubAmount();
//
//        this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);
//        WalletLog walletLog = this.walletLogMapper.toEntity(request, loginUserNo);
//        this.walletCashTotalRepository.updateWalletCashAdd(request.getAdAccountId(), request.getCashId(), amount, availableAmount);
//        this.walletLogRepository.save(walletLog);
    }

    public void saveFreeCash(WalletDto.Request.SaveFreeCash request, Integer loginUserNo) {
//        WalletFreeCash walletFreeCash = this.walletFreeCashMapper.toEntity(request, loginUserNo);
//        this.walletFreeCashRepository.save(walletFreeCash);
    }

    public void updateFreeCashStatus(Integer id, String status, Integer loginUserNo) {
//        if(status.equals("USED")) {
//            WalletFreeCash walletFreeCash = walletFreeCashRepository.findById(id).orElseThrow(CashNotFoundException::new);
//
//            if (walletFreeCash.getStatus().toString().equals("READY")) {
//                WalletDto.Response.WalletCashTotal list = walletCashTotalRepository.getCashTotalByCashId(walletFreeCash.getAdAccountId(), walletFreeCash.getCashId());
//                // 총합캐시
//                Long prevAmount = list.getAmount();
//                // 가용캐시
//                Long prevAvailableAmount = list.getAvailableAmount();
//
//                // total 캐시 변경
//                Long amount = prevAmount + walletFreeCash.getPubAmount();
//                Long availableAmount = prevAvailableAmount + walletFreeCash.getPubAmount();
//
//                WalletDto.Request.SaveCash request = new WalletDto.Request.SaveCash();
//                request.setAdAccountId(walletFreeCash.getAdAccountId());
//                request.setCashId(walletFreeCash.getCashId());
//                request.setSummary("charge");
//                request.setInAmount(walletFreeCash.getPubAmount());
//                request.setOutAmount(0L);
//                request.setMemo("use free cash");
//
//                this.adAccountRepository.outOfBalanceUpdate(request.getAdAccountId(), false);
//                WalletLog walletLog = this.walletLogMapper.toEntity(request, loginUserNo);
//                this.walletCashTotalRepository.updateWalletCashAdd(request.getAdAccountId(), request.getCashId(), amount, availableAmount);
//                this.walletLogRepository.save(walletLog);
//
//                this.walletFreeCashRepository.updateFreeCashStats(id, status, loginUserNo);
//            }
//        } else {
//            this.walletFreeCashRepository.updateFreeCashStats(id, status, loginUserNo);
//        }
    }
}
