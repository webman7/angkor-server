package com.adplatform.restApi.batch.api;

import com.adplatform.restApi.batch.service.BatchFirstSaveService;
import com.adplatform.restApi.batch.service.BatchMonthSaveService;
import com.adplatform.restApi.batch.service.BatchSaveService;
import com.adplatform.restApi.batch.service.BatchSecondSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchCommandApi {

    private final BatchSaveService batchSaveService;
    private final BatchFirstSaveService batchFirstSaveService;
    private final BatchSecondSaveService batchSecondSaveService;
    private final BatchMonthSaveService batchMonthSaveService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/daily")
    public void batchDaily(@RequestParam(value="exeDate", defaultValue="0") Integer exeDate) {
        this.batchFirstSaveService.batchJob(exeDate);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.batchSecondSaveService.batchJob(exeDate);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.batchMonthSaveService.batchJob(exeDate);
    }
}
