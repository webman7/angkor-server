package com.adplatform.restApi.infra.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String save(MultipartFile file);

    byte[] findByName(String filename);

    void delete(String url);
}
