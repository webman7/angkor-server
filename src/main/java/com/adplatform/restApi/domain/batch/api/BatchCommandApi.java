package com.adplatform.restApi.domain.batch.api;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.batch.dto.BatchStatusDto;
import com.adplatform.restApi.domain.batch.service.BatchSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchCommandApi {

    private final BatchSaveService batchSaveService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/daily")
    public void batchDaily(@RequestParam(value="exeDate", defaultValue="0") Integer exeDate) {
        this.batchSaveService.settlementDaily(exeDate);
    }
}
