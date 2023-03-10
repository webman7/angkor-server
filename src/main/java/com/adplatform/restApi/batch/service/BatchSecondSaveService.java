package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.dto.BatchStatusMapper;
import com.adplatform.restApi.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RequiredArgsConstructor
@Transactional
@Service
public class BatchSecondSaveService {
    private final BatchQueryMapper batchQueryMapper;
    private final BatchSaveQueryMapper batchSaveQueryMapper;
    private final BatchStatusMapper batchStatusMapper;
    private final BatchStatusRepository batchStatusRepository;

    public void batchJob(Integer reportDate) {
        ////////////////////////////////////////////////////////////
        // Daily Batch
        ////////////////////////////////////////////////////////////
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE, -1);
        String chkDate = SDF.format(calendar.getTime());

        int exeDate = 0;
        if (reportDate.equals(0)) {
            exeDate = Integer.parseInt(chkDate);
        } else {
            exeDate = Integer.parseInt(CommonUtils.getBeforeYearMonthDayByYMD(String.valueOf(reportDate), 1));
        }

        this.adAccountSettlementDaily(exeDate);
    }

    public void adAccountSettlementDaily(Integer exeDate) {

        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "D";
        String batchName = "adaccount_settlement_daily";
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 선행 작업 체크
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int repCnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, exeDate, "sale_amount_daily");
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
//        this.batchSaveQueryMapper.insertAdAccountSettlementDaily(exeDate);
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
