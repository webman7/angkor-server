package com.adplatform.restApi.domain.advertiser.creative.dto;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeMediaCategory;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        config = BaseMapperConfig.class
)
public abstract class CreativeMediaCategoryMapper {

    public abstract CreativeMediaCategory toEntity(Integer creativeId, Integer categoryId, Integer mediaId);

//    @Mapping(target = "creativeId", source = "creativeId")
//    @Mapping(target = "categoryId", source = "categoryId")
//    @Mapping(target = "mediaId", source = "mediaId")
//    public abstract CreativeDto.Response.Category toDetailResponse(CreativeMediaCategory creativeMediaCategory);
}
