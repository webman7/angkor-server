package com.adplatform.restApi.batch.api;

import com.adplatform.restApi.batch.service.*;
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
    private final BatchThirdSaveService batchThirdSaveService;
    private final BatchMonthSaveService batchMonthSaveService;
    private final BatchOperationSaveService batchOperationSaveService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/settlement")
    public void settlement(@RequestParam(value="exeDate", defaultValue="0") Integer exeDate) {
        // 캠페인별 일 정산, 비즈니스별 월 정산
        this.batchFirstSaveService.batchJob(exeDate);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 월 세금계산서, 캠페인 예약금액을 정산(선불)
        this.batchSecondSaveService.batchJob(exeDate);
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 종료 캠페인 정산(선불)
        this.batchThirdSaveService.batchJob(exeDate);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/operation/status")
    public void operationStatusChange(@RequestParam(value="exeDate", defaultValue="0") Integer exeDate) {
        // 캠페인, 광고그룹 LIVE, FINISHED 를 체크한다
        this.batchOperationSaveService.batchJob(exeDate);
    }


//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/daily")
//    public void batchDaily(@RequestParam(value="exeDate", defaultValue="0") Integer exeDate) {
//        this.batchFirstSaveService.batchJob(exeDate);
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        this.batchSecondSaveService.batchJob(exeDate);
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        this.batchMonthSaveService.batchJob(exeDate);
//    }

}
