package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AdAccountSaveService {
    private final AdAccountRepository adAccountRepository;
    private final AdAccountMapper adAccountMapper;
    private final UserQueryService userQueryService;

    public void save(AdAccountDto.Request.Save request, Integer loginUserId) {
        User user = this.userQueryService.findByIdOrElseThrow(loginUserId);
        AdAccount adAccount = this.adAccountMapper.toEntity(request, user)
                .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.RequestStatus.Y)
                .changeWalletMaster(WalletMaster.create());
        this.adAccountRepository.save(adAccount);
    }
}
