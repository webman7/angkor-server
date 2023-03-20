package com.adplatform.restApi.domain.media.dto.category;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import com.adplatform.restApi.global.value.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class, imports = Email.class)
public interface CategoryMapper {

    Category toEntity(CategoryDto.Request.Save request);
}
