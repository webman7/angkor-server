package com.adplatform.restApi.domain.media.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = BaseMapperConfig.class)
public abstract class MediaMapper {
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "status", expression = "java(Media.Status.N)")
    public abstract Media toEntity(MediaDto.Request.Save request, Company company);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "status", expression = "java(Media.Status.N)")
    public abstract Media toCategoryEntity(MediaDto.Request.Save request, Company company, List<Category> category);

    @Named("mapCategory")
    List<Integer> mapCategory(Collection<Category> category) {
        return category.stream().map(Category::getId).collect(Collectors.toList());
    }

    public abstract MediaDto.Response.Default toDefaultResponse(Media media);

    public abstract List<MediaDto.Response.Default> toDefaultResponse(List<Media> medias);
}
