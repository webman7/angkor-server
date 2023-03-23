package com.adplatform.restApi.domain.media.dto.placement;

import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface PlacementMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "status", expression = "java(Placement.Status.Y)")
    Placement toEntity(PlacementDto.Request.Save request);

    PlacementDto.Response.PlacementInfo toResponse(Placement placement);
}
