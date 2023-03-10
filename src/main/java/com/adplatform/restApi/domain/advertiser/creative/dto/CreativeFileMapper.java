package com.adplatform.restApi.domain.advertiser.creative.dto;

import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author junny
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface CreativeFileMapper {
    @Mapping(target = "fileType", source = "creativeFile.information.fileType")
    @Mapping(target = "fileSize", source = "creativeFile.information.fileSize")
    @Mapping(target = "filename", source = "creativeFile.information.filename")
    @Mapping(target = "originalFileName", source = "creativeFile.information.originalFileName")
    @Mapping(target = "url", source = "creativeFile.information.url")
    @Mapping(target = "width", source = "creativeFile.information.width")
    @Mapping(target = "height", source = "creativeFile.information.height")
    @Mapping(target = "mimeType", source = "creativeFile.information.mimeType")
    CreativeFileDto toDto(CreativeFile creativeFile);

    @Mapping(target = "fileType", source = "opinionProofFile.information.fileType")
    @Mapping(target = "fileSize", source = "opinionProofFile.information.fileSize")
    @Mapping(target = "filename", source = "opinionProofFile.information.filename")
    @Mapping(target = "originalFileName", source = "opinionProofFile.information.originalFileName")
    @Mapping(target = "url", source = "opinionProofFile.information.url")
    @Mapping(target = "width", source = "opinionProofFile.information.width")
    @Mapping(target = "height", source = "opinionProofFile.information.height")
    @Mapping(target = "mimeType", source = "opinionProofFile.information.mimeType")
    CreativeOpinionProofFileDto toOpinionProofFileDto(CreativeOpinionProofFile opinionProofFile);
}
