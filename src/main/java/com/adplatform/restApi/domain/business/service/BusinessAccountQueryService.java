package com.adplatform.restApi.domain.business.service;

import com.adplatform.restApi.domain.business.dao.account.BusinessAccountRepository;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.business.exception.BusinessAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BusinessAccountQueryService {

    private final BusinessAccountRepository businessAccountRepository;
    public BusinessAccount findByIdOrElseThrow(Integer id) {
        return this.businessAccountRepository.findById(id)
                .orElseThrow(BusinessAccountNotFoundException::new);
    }
}
