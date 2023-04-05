package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillFileDto;
import com.adplatform.restApi.domain.statistics.dto.MediaTaxBillPaymentFileDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaTaxBillQuerydslRepository {
    Page<TaxBillDto.Response.TaxBill> searchTax(Pageable pageable, TaxBillDto.Request.SearchTax searchRequest);

    Page<TaxBillDto.Response.TaxBill> searchSettlement(Pageable pageable, TaxBillDto.Request.SearchTax searchRequest);

    MediaTaxBillFileDto.Response.FileInfo findByMediaIdFileInfo(Integer id);

    MediaTaxBillPaymentFileDto.Response.FileInfo findByMediaIdPaymentFileInfo(Integer id);
}
