package com.adplatform.restApi.domain.adgroup.dto.media;

import com.adplatform.restApi.domain.adgroup.domain.Media;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface MediaMapper {
    MediaDto.Response.Default toDefaultResponse(Media media);

    List<MediaDto.Response.Default> toDefaultResponse(List<Media> medias);
}
