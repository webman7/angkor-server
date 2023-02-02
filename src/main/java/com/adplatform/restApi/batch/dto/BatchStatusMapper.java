package com.adplatform.restApi.batch.dto;

import com.adplatform.restApi.batch.domain.BatchStatus;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public abstract class BatchStatusMapper {
    public abstract BatchStatus toEntity(BatchStatusDto.Request.Save SaveDto);
}
