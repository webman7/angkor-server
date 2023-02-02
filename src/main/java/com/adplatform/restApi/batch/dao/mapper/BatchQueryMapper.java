package com.adplatform.restApi.batch.dao.mapper;

import com.adplatform.restApi.batch.dto.BatchStatusDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchQueryMapper {

    List<BatchStatusDto.Response.ReportAdGroupCost> reportAdGroupCost();

    BatchStatusDto.Request.Search getBatchStatusYN(String type, Integer exeDate, String name);

    int getBatchStatusYNCount(String type, Integer exeDate, String name);

    List<BatchStatusDto.Response.WalletCashTotal> walletCashTotal(Integer adAccountId);


}
