package com.adplatform.restApi.domain.advertiser.report.dao.custom;

import com.adplatform.restApi.domain.advertiser.report.dto.custom.ReportCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportCustomQuerydslRepository {

    Page<ReportCustomDto.Response.Default> search(Pageable pageable, ReportCustomDto.Request.Search request);

    ReportCustomDto.Response.Default reportCustomDetailInfo(Integer id);
}
