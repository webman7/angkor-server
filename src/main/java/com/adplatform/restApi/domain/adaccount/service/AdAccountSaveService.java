package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.wallet.dao.cash.CashRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class AdAccountSaveService {
    private final AdAccountRepository adAccountRepository;
    private final CashRepository cashRepository;
    private final AdAccountMapper adAccountMapper;
    private final UserQueryService userQueryService;

    public void save(AdAccountDto.Request.Save request, Integer loginUserId) {
        User user = this.userQueryService.findByIdOrElseThrow(loginUserId);
        AdAccount adAccount = this.adAccountMapper.toEntity(request, user)
                .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.RequestStatus.Y)
                .changeWalletMaster(WalletMaster.create(), this.cashRepository.findAll());
        this.adAccountRepository.save(adAccount);
    }

    public void creditLimitUpdate(AdAccountDto.Request.CreditLimitUpdate request) {
        AdAccountFindUtils.findByIdOrElseThrow(request.getId(), this.adAccountRepository).creditLimitUpdate(request);
    }

    public void changeConfig(Integer id, AdAccount.Config config) {
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(id, this.adAccountRepository);
        if (config == AdAccount.Config.ON) adAccount.changeConfigOn();
        else if (config == AdAccount.Config.OFF) adAccount.changeConfigOff();
    }
}
