package com.adplatform.restApi.domain.media.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = BaseMapperConfig.class)
public interface MediaMapper {
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "status", expression = "java(Media.Status.N)")
    Media toEntity(MediaDto.Request.Save request, Company company);

    @Mapping(target = "id", source = "media.id")
    @Mapping(target = "name", source = "media.name")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "url", source = "media.url")
    @Mapping(target = "mediaFiles", source = "mediaFile")
    MediaDto.Response.MediaInfo toCategoryResponse(Media media, Company company, List<Category> category, MediaFileDto.Response.FileInfo mediaFile);

    @Named("mapCategory")
    default List<Integer> mapCategory(Collection<Category> category) {
        return category.stream().map(Category::getId).collect(Collectors.toList());
    }

    MediaDto.Response.Default toDefaultResponse(Media media);

    List<MediaDto.Response.Default> toDefaultResponse(List<Media> medias);

    @Mapping(target = "id", source = "media.id")
    @Mapping(target = "name", source = "media.name")
    List<MediaDto.Response.Default> toMediaResponse(List<MediaDto.Response.Default> media);
}
