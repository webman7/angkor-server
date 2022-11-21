package com.adplatform.restApi.infra.file.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile({"prod"})
@Service
public class AwsS3FileServiceImpl implements FileService {
    @Override
    public String save(MultipartFile file) {
        return null;
    }

    @Override
    public byte[] findByName(String filename) {
        return new byte[0];
    }

    @Override
    public void delete(String filename) {

    }
}
