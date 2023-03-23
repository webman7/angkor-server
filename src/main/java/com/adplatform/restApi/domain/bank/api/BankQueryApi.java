package com.adplatform.restApi.domain.bank.api;

import com.adplatform.restApi.domain.bank.dao.BankRepository;
import com.adplatform.restApi.domain.bank.dto.BankDto;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bank")
public class BankQueryApi {
    private final BankRepository bankRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public List<BankDto.Response.BankInfo> bankInfo() {
        return this.bankRepository.bankInfo();
    }
}
