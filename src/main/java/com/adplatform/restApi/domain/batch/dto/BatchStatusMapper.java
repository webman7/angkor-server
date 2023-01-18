package com.adplatform.restApi.domain.batch.dto;

import com.adplatform.restApi.domain.batch.domain.BatchStatus;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class BatchStatusMapper {
    public abstract BatchStatus toEntity(BatchStatusDto.Request.Save SaveDto);
}
