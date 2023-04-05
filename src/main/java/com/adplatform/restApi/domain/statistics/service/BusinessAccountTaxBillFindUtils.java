package com.adplatform.restApi.domain.statistics.service;

import com.adplatform.restApi.domain.business.exception.BusinessAccountNotFoundException;
import com.adplatform.restApi.domain.media.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.statistics.dao.taxbill.BusinessAccountTaxBillRepository;
import com.adplatform.restApi.domain.statistics.dao.taxbill.MediaTaxBillRepository;
import com.adplatform.restApi.domain.statistics.domain.taxbill.BusinessAccountTaxBill;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;

public class BusinessAccountTaxBillFindUtils {
    public static BusinessAccountTaxBill findByIdOrElseThrow(Integer id, BusinessAccountTaxBillRepository repository) {
        return repository.findById(id)
                .orElseThrow(BusinessAccountNotFoundException::new);
    }
}
