package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.advertiser.creative.domain.Creative;
import com.adplatform.restApi.advertiser.creative.dto.CreativeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//@Profile({"prod"})
@Service
public class AwsS3FileServiceImpl implements FileService {
    @Override
    public String save(CreativeDto.Request.Save request, MultipartFile file) {
        return null;
    }

    @Override
    public String saveProofFile(Creative creative, MultipartFile file) {
        return null;
    }

    @Override
    public byte[] findByName(String filename) {
        return new byte[0];
    }

    @Override
    public void delete(Integer adGroupId, String filename) {

    }

    @Override
    public void deleteOpinionProofFiles(Integer adGroupId, String filename) {

    }
}