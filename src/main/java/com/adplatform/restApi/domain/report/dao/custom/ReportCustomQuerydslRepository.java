package com.adplatform.restApi.domain.report.dao.custom;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.report.dto.custom.ReportCustomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportCustomQuerydslRepository {

    Page<ReportCustomDto.Response.Default> search(Pageable pageable, ReportCustomDto.Request.Search request);
}
