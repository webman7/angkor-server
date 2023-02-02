package com.adplatform.restApi.advertiser.report.dto.custom;

import com.adplatform.restApi.advertiser.report.domain.ReportCustom;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface ReportCustomMapper {

    ReportCustom toEntity(ReportCustomDto.Request.Save request);
}
