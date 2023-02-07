package com.adplatform.restApi.domain.advertiser.report.service;

import com.adplatform.restApi.domain.advertiser.report.dao.custom.ReportCustomRepository;
import com.adplatform.restApi.domain.advertiser.report.domain.ReportCustom;
import com.adplatform.restApi.domain.advertiser.report.dto.custom.ReportCustomDto;
import com.adplatform.restApi.domain.advertiser.report.dto.custom.ReportCustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportSaveService {

    private final ReportCustomRepository reportCustomRepository;
    private final ReportCustomMapper reportCustomMapper;

    public void save(ReportCustomDto.Request.Save request, Integer loginUserNo) {
        ReportCustom reportCustom = this.reportCustomMapper.toEntity(request);
        this.reportCustomRepository.save(reportCustom);
    }

    public void update(ReportCustomDto.Request.Update request) {
        ReportCustomFindUtils.findByIdOrElseThrow(request.getId(), this.reportCustomRepository).update(request);
    }

    public void delete(Integer id) {
        ReportCustomFindUtils.findByIdOrElseThrow(id, this.reportCustomRepository).delete();
    }
}
