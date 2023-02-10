package com.adplatform.restApi.batch.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchSaveQueryMapper {

    void insertAdAccountSettlementDaily(Integer statDate);
    void insertMediaSettlementDaily(Integer statDate);
    void insertAdAccountSettlementMonthly(Integer startDate, Integer endDate);
    void insertAdAccountTaxBillMonthly(Integer startDate, Integer endDate);
    void insertMediaSettlementMonthly(Integer startDate, Integer endDate);
    void insertMediaTaxBillMonthly(Integer startDate, Integer endDate);
}
