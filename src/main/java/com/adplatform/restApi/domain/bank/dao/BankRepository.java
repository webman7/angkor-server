package com.adplatform.restApi.domain.bank.dao;

import com.adplatform.restApi.domain.bank.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer>, BankQuerydslRepository {
}
