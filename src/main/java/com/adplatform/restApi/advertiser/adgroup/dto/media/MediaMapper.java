package com.adplatform.restApi.advertiser.adgroup.dto.media;

import com.adplatform.restApi.advertiser.adgroup.domain.Media;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface MediaMapper {
    MediaDto.Response.Default toDefaultResponse(Media media);

    List<MediaDto.Response.Default> toDefaultResponse(List<Media> medias);
}