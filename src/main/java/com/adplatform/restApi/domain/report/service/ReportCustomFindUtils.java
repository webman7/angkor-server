package com.adplatform.restApi.domain.report.service;

import com.adplatform.restApi.domain.campaign.exception.CampaignNotFoundException;
import com.adplatform.restApi.domain.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.domain.report.domain.ReportCustom;
import com.adplatform.restApi.domain.report.exception.ReportCustomNotFoundException;

public class ReportCustomFindUtils {
    public static ReportCustom findByIdOrElseThrow(Integer id, ReportCustomRepository repository) {
        return repository.findById(id).orElseThrow(ReportCustomNotFoundException::new);
    }
}
