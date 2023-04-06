package com.adplatform.restApi.batch.service;

import com.adplatform.restApi.batch.dao.mapper.BatchSaveQueryMapper;
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
public class BatchOperationSaveService {
    private final BatchSaveQueryMapper batchSaveQueryMapper;
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

        // 캠페인, 광고그룹 LIVE, FINISHED 를 체크한다
        this.operationStatusJob(exeDate);
    }

    public void operationStatusJob(Integer exeDate) {
        this.batchSaveQueryMapper.updateCampaignLive(exeDate);
        this.batchSaveQueryMapper.updateCampaignFinished(exeDate);
        this.batchSaveQueryMapper.updateAdGroupLive(exeDate);
        this.batchSaveQueryMapper.updateAdGroupFinished(exeDate);
    }
}
