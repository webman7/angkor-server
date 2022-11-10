package com.adplatform.restApi.domain.adaccount.service;

import com.adplatform.restApi.domain.adaccount.dao.adaccount.AdAccountRepository;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdAccountQueryService {
    private final AdAccountRepository adAccountRepository;

    public AdAccount findById(Integer id) {
        return this.adAccountRepository.findById(id).orElseThrow(AdAccountNotFoundException::new);
    }
}
