package com.adplatform.restApi.domain.advertiser.creative.dto;

import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeLanding;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author junny
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
    @Mapping(target = "adGroup", expression = ("java(AdGroupFindUtils.findByIdOrElseThrow(dto.getAdGroupId(), this.adGroupRepository))"))
    @Mapping(target = "files", ignore = true)
    @Mapping(target = "opinionProofFiles", ignore = true)
    @Mapping(target = "landing", source = "dto", qualifiedByName = "mapCreativeLanding")
    @Mapping(target = "placements", source = "placements")
    @Mapping(target = "config", expression = "java(Creative.Config.ON)")
    @Mapping(target = "systemConfig", expression = "java(Creative.SystemConfig.ON)")
    @Mapping(target = "reviewStatus", expression = "java(Creative.ReviewStatus.WAITING)")
    @Mapping(target = "status", expression = "java(Creative.Status.UNAPPROVED)")
//    @Mapping(target = "update", ignore = true)
    @Mapping(target = "copy", ignore = true)
    public abstract Creative toEntity(CreativeDto.Request.Save dto, List<Placement> placements);

    @Named("mapCreativeLanding")
    public CreativeLanding mapCreativeLanding(CreativeDto.Request.Save dto) {
        return new CreativeLanding(
                dto.getLandingType(),
                dto.getPcLandingUrl(),
                dto.getMobileLandingUrl(),
                dto.getResponsiveLandingUrl());
    }

    @Mapping(target = "files", source = "creativeFile")
    @Mapping(target = "opinionProofFiles", source = "creativeOpinionProofFile")
    @Mapping(target = "placements", source = "creative.placements", qualifiedByName = "mapPlacements")
    @Mapping(target = "name", source = "creative.name")
    @Mapping(target = "adGroupName", source = "adGroup.name")
    @Mapping(target = "campaign.name", source = "adGroup.campaign.name")
    @Mapping(target = "campaign.adTypeAndGoal.adTypeName", source = "adGroup.campaign.adTypeAndGoal.adType.name")
    @Mapping(target = "campaign.adTypeAndGoal.adGoalName", source = "adGroup.campaign.adTypeAndGoal.adGoal.name")
    public abstract CreativeDto.Response.Detail toDetailResponse(Creative creative, List<CreativeFile> creativeFile, List<CreativeOpinionProofFile> creativeOpinionProofFile, AdGroup adGroup);

    @Named("mapPlacements")
    List<Integer> mapPlacement(Collection<Placement> placements) {
        return placements.stream().map(Placement::getId).collect(Collectors.toList());
    }
}
