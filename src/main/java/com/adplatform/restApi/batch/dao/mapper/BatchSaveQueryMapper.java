package com.adplatform.restApi.batch.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BatchSaveQueryMapper {

    void insertCampaignSettlementDaily(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    void insertBusinessAccountSettlementMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    void insertBusinessAccountTaxBillMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    void updateCampaignLive(@Param("statDate") Integer statDate);
    void updateCampaignFinished(@Param("statDate") Integer statDate);
    void updateAdGroupLive(@Param("statDate") Integer statDate);
    void updateAdGroupFinished(@Param("statDate") Integer statDate);




    void insertAdAccountSettlementDaily(@Param("statDate") Integer statDate);
    void insertMediaSettlementDaily(@Param("statDate") Integer statDate);
    void insertAdAccountSettlementMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    void insertAdAccountTaxBillMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);

    void insertMediaSettlementMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);
    void insertMediaTaxBillMonthly(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);



}
