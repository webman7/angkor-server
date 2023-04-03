package com.adplatform.restApi.batch.dao.mapper;

import com.adplatform.restApi.batch.dto.BatchStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchQueryMapper {

    List<BatchStatusDto.Response.ReportAdGroupCost> reportAdGroupCost(@Param("exeDate") Integer exeDate);

    BatchStatusDto.Request.Search getBatchStatusYN(@Param("type") String type, @Param("exeDate") Integer exeDate, @Param("name") String name);

    int getBatchStatusYNCount(@Param("type") String type, @Param("exeDate") Integer exeDate, @Param("name") String name);

    List<BatchStatusDto.Response.WalletCashTotal> walletCashTotal(@Param("adAccountId") Integer adAccountId);

     BatchStatusDto.Response.TaxNo getBusinessAccountTaxBillMaxTaxNo(@Param("endDate") Integer endDate);


}
