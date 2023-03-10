package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountMapper;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.agency.businessright.dto.BusinessRightRequestMapper;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.service.BusinessAccountQueryService;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
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
    private final AdAccountMapper adAccountMapper;
    private final UserQueryService userQueryService;
    private final BusinessAccountQueryService businessAccountQueryService;
    private final CompanyService companyService;
    private final WalletMasterRepository walletMasterRepository;

    public void save(AdAccountDto.Request.Save request, Integer loginUserNo) {
        User user = this.userQueryService.findByIdOrElseThrow(loginUserNo);
        BusinessAccount businessAccount = this.businessAccountQueryService.findByIdOrElseThrow(request.getBusinessAccountId());
//        Company ownerCompany = this.companyService.findByIdOrElseThrow(request.getOwnerCompany().getId());
//        if(user.getCompany().getType().toString().equals("ADVERTISER")) {
//            request.setAgencyRegister(false);
////            AdAccount adAccount = this.adAccountMapper.toEntity(request, user)
////                    .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.RequestStatus.Y)
////                    .changeWalletMaster(WalletMaster.create(), this.cashRepository.findAll());
////            this.adAccountRepository.save(adAccount);
//        } else if(user.getCompany().getType().toString().equals("AGENCY")) {
//            request.setAgencyRegister(true);
//        }

        AdAccount adAccount = this.adAccountMapper.toEntity(request, businessAccount)
                .addAdAccountUser(user, AdAccountUser.MemberType.MASTER, AdAccountUser.Status.Y);
        this.adAccountRepository.save(adAccount);

//        System.out.println("==========================================================");
//        this.walletMasterRepository.openDateUpdate(1, Integer.getInteger(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        System.out.println(WalletFindUtils.findByIdOrElseThrow(1, this.walletMasterRepository));
    }

//    public void creditLimitUpdate(AdAccountDto.Request.CreditLimitUpdate request) {
//        AdAccountFindUtils.findByIdOrElseThrow(request.getId(), this.adAccountRepository).creditLimitUpdate(request);
//    }

    public void changeConfig(Integer id, AdAccount.Config config) {
        AdAccount adAccount = AdAccountFindUtils.findByIdOrElseThrow(id, this.adAccountRepository);
        if (config == AdAccount.Config.ON) adAccount.changeConfigOn();
        else if (config == AdAccount.Config.OFF) adAccount.changeConfigOff();
    }
}
