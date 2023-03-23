package com.adplatform.restApi.domain.bank.dao;

import com.adplatform.restApi.domain.bank.dto.BankDto;

import java.util.List;

public interface BankQuerydslRepository {

    List<BankDto.Response.BankInfo> bankInfo();
}
