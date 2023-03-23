package com.adplatform.restApi.domain.media.dto.placement;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = BaseMapperConfig.class)
public interface MediaPlacementMapper {

    @Mapping(target = "media", source = "media")
    @Mapping(target = "placement", source = "placement")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "width", source = "request.width")
    @Mapping(target = "height", source = "request.height")
    @Mapping(target = "widthHeightRate", source = "request.widthHeightRate")
    @Mapping(target = "url", source = "request.url")
    @Mapping(target = "memo", source = "request.memo")
    @Mapping(target = "adminMemo", source = "request.adminMemo")
    @Mapping(target = "status", expression = "java(MediaPlacement.Status.N)")
    MediaPlacement toEntity(MediaPlacementDto.Request.Save request, Media media, Placement placement);

    @Mapping(target = "id", source = "mediaPlacement.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(target = "mediaId", source = "media.id")
    @Mapping(target = "mediaName", source = "media.name")
    @Mapping(target = "name", source = "mediaPlacement.name")
    @Mapping(target = "url", source = "mediaPlacement.url")
    @Mapping(target = "memo", source = "mediaPlacement.memo")
    @Mapping(target = "adminMemo", source = "mediaPlacement.adminMemo")
    @Mapping(target = "status", source = "mediaPlacement.status")
    @Mapping(target = "regUserId", source = "user.loginId")
    @Mapping(target = "createdAt", source = "mediaPlacement.createdAt")
    MediaPlacementDto.Response.MediaPlacementInfo toResponse(MediaPlacement mediaPlacement, Media media, Company company, User user);
}
