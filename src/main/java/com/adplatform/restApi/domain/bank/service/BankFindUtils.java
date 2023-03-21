package com.adplatform.restApi.domain.bank.service;

import com.adplatform.restApi.domain.bank.dao.BankRepository;
import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.exception.BankNotFoundException;

public class BankFindUtils {
    public static Bank findByIdOrElseThrow(Integer id, BankRepository repository) {
        return repository.findById(id).orElseThrow(BankNotFoundException::new);
    }
}
