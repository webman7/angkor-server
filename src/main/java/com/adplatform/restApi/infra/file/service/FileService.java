package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface FileService {
    String save(CreativeDto.Request.Save request, MultipartFile file);

    String saveProofFile(Creative creative, MultipartFile file);

    byte[] findByName(String filename);

    void delete(Integer adGroupId, String filename);

    void deleteOpinionProofFiles(Integer adGroupId, String filename);
}
