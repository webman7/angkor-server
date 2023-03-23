package com.adplatform.restApi.domain.media.dto.category;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface CategoryMapper {

    @Mapping(target = "name", source = "request.name")
    Category toEntity(CategoryDto.Request.Save request);

    CategoryDto.Response.CategoryInfo toResponse(Category category);
}
