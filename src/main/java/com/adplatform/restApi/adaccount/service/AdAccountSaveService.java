package com.adplatform.restApi.adaccount.service;

import com.adplatform.restApi.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.adaccount.dto.adaccount.AdAccountMapper;
import com.adplatform.restApi.adaccount.domain.AdAccount;
import com.adplatform.restApi.agency.businessright.dto.BusinessRightRequestMapper;
import com.adplatform.restApi.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.company.domain.Company;
import com.adplatform.restApi.company.service.CompanyService;
import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.user.service.UserQueryService;
import com.adplatform.restApi.wallet.dao.cash.CashRepository;
import com.adplatform.restApi.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.wallet.domain.WalletMaster;
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
    private final CompanyService companyService;
    private final BusinessRightRequestMapper businessRightRequestMapper;
    private final WalletMasterRepository walletMasterRepository;

    public void save(AdAccountDto.Request.Save request, Integer loginUserNo) {
        User user = this.userQueryService.findByIdOrElseThrow(loginUserNo);
        Company ownerCompany = this.companyService.findByIdOrElseThrow(request.getOwnerCompany().getId());
//        if(user.getCompany().getType().toString().equals("ADVERTISER")) {
//            AdAccount adAccount = this.adAccountMapper.toEntity(request, user)
//                    .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.RequestStatus.Y)
//                    .changeWalletMaster(WalletMaster.create(), this.cashRepository.findAll());
//            this.adAccountRepository.save(adAccount);
//        } else if(user.getCompany().getType().toString().equals("AGENCY")) {
//
//        }

        AdAccount adAccount = this.adAccountMapper.toEntity(request, user)
                .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.RequestStatus.Y)
                .changeWalletMaster(WalletMaster.create(), this.cashRepository.findAll());
        this.adAccountRepository.save(adAccount);

//        System.out.println("==========================================================");
//        this.walletMasterRepository.openDateUpdate(1, Integer.getInteger(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        System.out.println(WalletFindUtils.findByIdOrElseThrow(1, this.walletMasterRepository));
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
