package com.adplatform.restApi.domain.media.dto.category;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface MediaCategoryMapper {

    MediaCategory toEntity(MediaCategoryDto.Request.Save request);
}
