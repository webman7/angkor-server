package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.BatchStatusRepository;
import com.adplatform.restApi.batch.dao.mapper.BatchQueryMapper;
import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
import com.adplatform.restApi.batch.domain.BatchStatus;
import com.adplatform.restApi.batch.dto.BatchStatusDto;
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
public class BatchFirstSaveService {
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

        // 6일날 전월 데이터를 정산한다.(캠페인별)
        if (String.valueOf(exeDate).endsWith("06")) {
            this.campaignSettlementMonthly(exeDate);
        }
    }

    public void campaignSettlementMonthly(Integer exeDate) {
        ////////////////////////////////////////////////////////////
        // Batch Code
        ////////////////////////////////////////////////////////////
        String batchType = "M";
        String batchName = "campaign_settlement_daily";

        String beforeMonthFirstDate = CommonUtils.getBeforeYearMonthByYMD(String.valueOf(exeDate), 1);
        String beforeMonthLastDate = CommonUtils.getLastDayOfMonthByYMD(beforeMonthFirstDate);

        beforeMonthFirstDate = beforeMonthFirstDate.substring(0, 6) + "01";

        ////////////////////////////////////////////////////////////
        // 진행 여부 확인
        ////////////////////////////////////////////////////////////
        // Batch Y Count
        int cnt = this.batchQueryMapper.getBatchStatusYNCount(batchType, Integer.parseInt(beforeMonthLastDate), batchName);
        if (cnt > 0) {
            return;
        }

        ////////////////////////////////////////////////////////////
        // 캠페인 일별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertCampaignSettlementDaily(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

        ////////////////////////////////////////////////////////////
        // 비즈니스 월별 정산
        ////////////////////////////////////////////////////////////
        this.batchSaveQueryMapper.insertBusinessAccountSettlementMonthly(Integer.parseInt(beforeMonthFirstDate), Integer.parseInt(beforeMonthLastDate));

        ////////////////////////////////////////////////////////////
        // 진행 완료
        ////////////////////////////////////////////////////////////
        // Batch Execution
        BatchStatusDto.Request.Save saveList = new BatchStatusDto.Request.Save();
        saveList.setType(batchType);
        saveList.setExeDate(Integer.parseInt(beforeMonthLastDate));
        saveList.setName(batchName);
        saveList.setExeYn(true);
        BatchStatus batchStatus = this.batchStatusMapper.toEntity(saveList);
        this.batchStatusRepository.save(batchStatus);

    }
}

