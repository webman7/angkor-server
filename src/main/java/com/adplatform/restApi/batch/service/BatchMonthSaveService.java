package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.dto.BatchStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RequiredArgsConstructor
@Transactional
@Service
public class BatchMonthSaveService {
    private final BatchQueryMapper batchQueryMapper;
    private final BatchSaveQueryMapper batchSaveQueryMapper;
    private final BatchStatusMapper batchStatusMapper;
    private final BatchStatusRepository batchStatusRepository;


    public void batchJob(Integer reportDate) {
        ////////////////////////////////////////////////////////////
        // Month Batch
        ////////////////////////////////////////////////////////////
        Calendar calendarMonth = new GregorianCalendar();
        SimpleDateFormat SDFMonth = new SimpleDateFormat("yyyyMMdd");
        String chkMonthDate = SDFMonth.format(calendarMonth.getTime());

        int exeMonthDate = 0;
        if (reportDate.equals(0)) {
            exeMonthDate = Integer.parseInt(chkMonthDate);
        } else {
            exeMonthDate = reportDate;
        }

//        if(String.valueOf(exeMonthDate).substring(6, 8).equals("01")) {
//            String BeforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeMonthDate), 1);
//            String BeforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(BeforeMonthFirstDate);
//            this.adAccountSettlementMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate));
//            try {
//                System.out.println("Sleep 3s: "  + LocalDateTime.now());
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            this.adAccountTaxBillMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate));
//            try {
//                System.out.println("Sleep 3s: "  + LocalDateTime.now());
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            // 매체 정산은 어떻게 할지 결정 후 추가
//            //            this.mediaSettlementMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate));
//            this.mediaTaxBillMonthly(Integer.parseInt(BeforeMonthFirstDate), Integer.parseInt(BeforeMonthLastDate));
//        }
    }

    public void adAccountSettlementMonthly(Integer firstDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "adaccount_settlement_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", exeDate, "adaccount_settlement_daily");
        if (repCnt == 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

//        ////////////////////////////////////////////////////////////
//        // 일별 정산
//        ////////////////////////////////////////////////////////////
//        this.batchSaveQueryMapper.insertAdAccountSettlementMonthly(firstDate, exeDate);
//
//        ////////////////////////////////////////////////////////////
//        // 진행 완료
//        ////////////////////////////////////////////////////////////
//        // Batch Execution
//        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
//        saveList.setType(batchType);
//        saveList.setExeDate(exeDate);
//        saveList.setName(batchName);
//        saveList.setExeYn(true);
//        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
//        this.batchStatusRepository.save(batchStatus);
    }

    public void mediaSettlementMonthly(Integer firstDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "media_settlement_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", exeDate, "media_settlement_daily");
        if (repCnt == 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

//        ////////////////////////////////////////////////////////////
//        // 일별 정산
//        ////////////////////////////////////////////////////////////
//        this.batchSaveQueryMapper.insertMediaSettlementMonthly(firstDate, exeDate);
//
//        ////////////////////////////////////////////////////////////
//        // 진행 완료
//        ////////////////////////////////////////////////////////////
//        // Batch Execution
//        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
//        saveList.setType(batchType);
//        saveList.setExeDate(exeDate);
//        saveList.setName(batchName);
//        saveList.setExeYn(true);
//        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
//        this.batchStatusRepository.save(batchStatus);
    }

    public void adAccountTaxBillMonthly(Integer firstDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "adaccount_tax_bill_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", exeDate, "adaccount_settlement_daily");
        if (repCnt == 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

//        ////////////////////////////////////////////////////////////
//        // 일별 정산
//        ////////////////////////////////////////////////////////////
//        this.batchSaveQueryMapper.insertAdAccountTaxBillMonthly(firstDate, exeDate);
//
//        ////////////////////////////////////////////////////////////
//        // 진행 완료
//        ////////////////////////////////////////////////////////////
//        // Batch Execution
//        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
//        saveList.setType(batchType);
//        saveList.setExeDate(exeDate);
//        saveList.setName(batchName);
//        saveList.setExeYn(true);
//        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
//        this.batchStatusRepository.save(batchStatus);
    }

    public void mediaTaxBillMonthly(Integer firstDate, Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "media_tax_bill_monthly";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount("D", exeDate, "media_settlement_daily");
        if (repCnt == 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, batchName);
        if (cnt > 0) {
            return;
        }

//        ////////////////////////////////////////////////////////////
//        // 일별 정산
//        ////////////////////////////////////////////////////////////
//        this.batchSaveQueryMapper.insertMediaTaxBillMonthly(firstDate, exeDate);
//
//        ////////////////////////////////////////////////////////////
//        // 진행 완료
//        ////////////////////////////////////////////////////////////
//        // Batch Execution
//        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
//        saveList.setType(batchType);
//        saveList.setExeDate(exeDate);
//        saveList.setName(batchName);
//        saveList.setExeYn(true);
//        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
//        this.batchStatusRepository.save(batchStatus);
    }
}
