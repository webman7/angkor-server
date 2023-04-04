package com.adplatform.restApi.domain.statistics.service;

import com.adplatform.restApi.domain.media.exception.MediaNotFoundException;
import com.adplatform.restApi.domain.statistics.dao.taxbill.MediaTaxBillRepository;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;

public class MediaTaxBillFindUtils {
    public static MediaTaxBill findByIdOrElseThrow(Integer id, MediaTaxBillRepository repository) {
        return repository.findById(id)
                .orElseThrow(MediaNotFoundException::new);
    }
}
