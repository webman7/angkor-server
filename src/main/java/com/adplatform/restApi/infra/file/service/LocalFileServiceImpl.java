package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author junny
 * @since 1.0
 */
//@Profile({"stage", "local", "dev"})
@Primary
@Service
public class LocalFileServiceImpl implements FileService {
    private static final String FILE_PATH = "./files/images/";
    private static final String FILE_PROOF_PATH = "./files/proof/";
    private static final String FILE_COMPANY_PATH = "./files/company/";

    @SneakyThrows
    @Override
    public String save(CreativeDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_PATH + request.getAdGroupId();
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("savedFile.toPath() : " + savedFile.toPath());
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public String saveProofFile(Creative creative, MultipartFile file) {
        String filePath = FILE_PROOF_PATH + creative.getAdGroup().getId();
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public String saveCompany(CompanyDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_COMPANY_PATH + request.getType();
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("savedCompanyFile.toPath() : " + savedFile.toPath());
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public byte[] findByName(String filename) {
        FileSystemResource resource = new FileSystemResource(filename);
        if (!resource.exists()) throw new IllegalArgumentException("Not Found File.");
        return IOUtils.toByteArray(resource.getInputStream());
    }

    @Override
    public void delete(Integer adGroupId, String filename) {
        FileUtils.deleteQuietly(new File(FILE_PATH + adGroupId + "/" + filename));
    }

    @Override
    public void deleteOpinionProofFiles(Integer adGroupId, String filename) {
        FileUtils.deleteQuietly(new File(FILE_PROOF_PATH + adGroupId + "/" + filename));
    }
}
