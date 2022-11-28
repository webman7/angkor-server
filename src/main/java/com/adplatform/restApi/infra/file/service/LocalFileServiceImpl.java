package com.adplatform.restApi.infra.file.service;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Profile({"local", "dev"})
@Service
public class LocalFileServiceImpl implements FileService {
    private static final String FILE_PATH = "./files/";

    @SneakyThrows
    @Override
    public String save(MultipartFile file) {
        File savedFile = new File(String.format(
                "%s%s.%s",
                FILE_PATH, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();
        file.transferTo(savedFile.toPath());
        return savedFile.getName();
    }

    @SneakyThrows
    @Override
    public byte[] findByName(String filename) {
        FileSystemResource resource = new FileSystemResource(FILE_PATH + filename);
        if (!resource.exists()) throw new IllegalArgumentException("해당 파일이 존재하지 않습니다.");
        return IOUtils.toByteArray(resource.getInputStream());
    }

    @Override
    public void delete(String filename) {
        FileUtils.deleteQuietly(new File(FILE_PATH + filename));
    }
}
