package com.adplatform.restApi.domain.report.dto.custom;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.report.domain.ReportCustom;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface ReportCustomMapper {

    ReportCustom toEntity(ReportCustomDto.Request.Save request);
}
