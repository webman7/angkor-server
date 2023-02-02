package com.adplatform.restApi.advertiser.report.service;

import com.adplatform.restApi.advertiser.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.advertiser.report.domain.ReportCustom;
import com.adplatform.restApi.advertiser.report.exception.ReportCustomNotFoundException;

public class ReportCustomFindUtils {
    public static ReportCustom findByIdOrElseThrow(Integer id, ReportCustomRepository repository) {
        return repository.findById(id).orElseThrow(ReportCustomNotFoundException::new);
    }
}
