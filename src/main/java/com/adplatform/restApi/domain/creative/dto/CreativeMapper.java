package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.domain.CreativeLanding;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(
        config = BaseMapperConfig.class,
        imports = AdGroupFindUtils.class,
        uses = CreativeFileMapper.class
)
public abstract class CreativeMapper {
    @Autowired
    protected AdGroupRepository adGroupRepository;

    @Mapping(target = "representativeId", constant = "1")
    @Mapping(target = "adGroup", expression = ("java(AdGroupFindUtils.findById(dto.getAdGroupId(), this.adGroupRepository))"))
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "opinionProofFiles", ignore = true)
    @Mapping(target = "landing", source = "dto", qualifiedByName = "mapCreativeLanding")
    @Mapping(target = "config", expression = "java(Creative.Config.ON)")
    @Mapping(target = "systemConfig", expression = "java(Creative.SystemConfig.ON)")
    @Mapping(target = "reviewStatus", expression = "java(Creative.ReviewStatus.WAITING)")
    @Mapping(target = "status", expression = "java(Creative.Status.UNAPPROVED)")
    public abstract Creative toEntity(CreativeDto.Request.Save dto);

    @Named("mapCreativeLanding")
    public CreativeLanding mapCreativeLanding(CreativeDto.Request.Save dto) {
        return new CreativeLanding(
                dto.getLandingType(),
                dto.getPcLandingUrl(),
                dto.getMobileLandingUrl(),
                dto.getResponsiveLandingUrl());
    }

    public abstract CreativeDto.Response.Detail toDetailResponse(Creative creative);
}
