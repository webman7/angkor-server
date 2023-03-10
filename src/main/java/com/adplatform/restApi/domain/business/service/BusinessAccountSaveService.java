package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountMapper;
import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.exception.CompanyAlreadyExistException;
import com.adplatform.restApi.domain.company.service.CompanyService;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.dao.walletmaster.WalletMasterRepository;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class BusinessAccountSaveService {
    private final BusinessAccountRepository businessAccountRepository;
    private final BusinessAccountMapper businessAccountMapper;
    private final UserQueryService userQueryService;
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    private final CompanyRepository companyRepository;

    public void save(BusinessAccountDto.Request.Save request, Integer loginUserNo) {

        // 회사 중복 체크
        CompanyDto.Request.SearchKeyword searchRequest = new CompanyDto.Request.SearchKeyword();
        searchRequest.setSearchKeyword(request.getRegistrationNumber());

        Integer companyCount = this.companyRepository.registrationNumberCount(searchRequest);

        if(!companyCount.equals(0)) {
            throw new CompanyAlreadyExistException();
        }

        // 회사 정보 인서트
        Company company = this.companyRepository.save(this.companyMapper.toBusinessEntity(request));
        // 인서트한 아이디를 가져온다.
        request.setCompanyId(company.getId());

        User user = this.userQueryService.findByIdOrElseThrow(loginUserNo);

        BusinessAccount businessAccount = this.businessAccountMapper.toEntity(request, company)
                .addBusinessAccountUser(user, BusinessAccountUser.MemberType.MASTER, BusinessAccountUser.AccountingYN.Y, BusinessAccountUser.Status.Y)
                .changeWalletMaster(WalletMaster.create());
        this.businessAccountRepository.save(businessAccount);

//        System.out.println("==========================================================");
//        this.walletMasterRepository.openDateUpdate(1, Integer.getInteger(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        System.out.println(WalletFindUtils.findByIdOrElseThrow(1, this.walletMasterRepository));
    }

    public void creditLimitUpdate(BusinessAccountDto.Request.CreditLimitUpdate request) {
        BusinessAccountFindUtils.findByIdOrElseThrow(request.getId(), this.businessAccountRepository).creditLimitUpdate(request);
    }

    public void changeConfig(Integer id, BusinessAccount.Config config) {
        BusinessAccount businessAccount = BusinessAccountFindUtils.findByIdOrElseThrow(id, this.businessAccountRepository);
        if (config == BusinessAccount.Config.ON) businessAccount.changeConfigOn();
        else if (config == BusinessAccount.Config.OFF) businessAccount.changeConfigOff();
    }
}
