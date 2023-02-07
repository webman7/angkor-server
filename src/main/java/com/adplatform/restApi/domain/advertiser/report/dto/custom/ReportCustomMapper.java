package com.adplatform.restApi.domain.advertiser.report.dto.custom;

import com.adplatform.restApi.domain.advertiser.report.domain.ReportCustom;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface ReportCustomMapper {

    ReportCustom toEntity(ReportCustomDto.Request.Save request);
}
