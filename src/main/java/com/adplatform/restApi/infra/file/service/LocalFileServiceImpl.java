package com.adplatform.restApi.infra.file.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Profile({"local", "dev"})
@Service
public class LocalFileServiceImpl implements FileService {
    private static final String FILE_PATH = "./files/";
    @SneakyThrows
    @Override
    public String save(MultipartFile file) {
        File savedFile = new File(String.format("%s%s.%s", FILE_PATH, UUID.randomUUID(), FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();
        file.transferTo(savedFile.toPath());
        return savedFile.getPath();
    }

    @Override
    public byte[] findByName(String filename) {
        return new byte[0];
    }

    @Override
    public void delete(String url) {

    }
}
