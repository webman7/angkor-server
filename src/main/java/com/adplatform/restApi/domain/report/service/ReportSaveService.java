package com.adplatform.restApi.domain.report.service;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.domain.report.domain.ReportCustom;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.wallet.domain.WalletMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportSaveService {

    private final ReportCustomRepository reportCustomRepository;
    private final ReportCustomMapper reportCustomMapper;

    public void save(ReportCustomDto.Request.Save request, Integer loginUserId) {
        ReportCustom reportCustom = this.reportCustomMapper.toEntity(request);
        this.reportCustomRepository.save(reportCustom);
    }
}
