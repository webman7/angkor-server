package com.adplatform.restApi.batch.api;

import com.adplatform.restApi.batch.service.BatchSaveService;
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
        this.batchSaveService.batchJob(exeDate);
    }
}
