package com.adplatform.restApi.batch.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchSaveQueryMapper {

    void insertAdAccountSettlementDaily(Integer statDate);
    void insertMediaSettlementDaily(Integer statDate);
    void insertAdAccountSettlementMonthly(Integer statDate, Integer lashDate, Integer exeDate);
    void insertAdAccountTaxBillMonthly(Integer statDate, Integer lashDate, Integer exeDate);
    void insertMediaSettlementMonthly(Integer statDate, Integer lashDate, Integer exeDate);
    void insertMediaTaxBillMonthly(Integer statDate, Integer lashDate, Integer exeDate);
}
