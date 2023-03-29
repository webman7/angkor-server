package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
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
    private static final String FILE_MEDIA_PATH = "./files/media/";
    private static final String FILE_MEDIA_PLACEMENT_PATH = "./files/placement/";
    private static final String FILE_CHARGE_PATH = "./files/charge/";
    private static final String FILE_REFUND_PATH = "./files/refund/";

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
    public String saveCompany(CompanyDto.Request.Save request, MultipartFile file, String fType) {
        String filePath = FILE_COMPANY_PATH + fType;
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
    public String saveWalletCharge(WalletDto.Request.SaveCredit request, MultipartFile file) {
        String filePath = FILE_CHARGE_PATH + "images";
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("saveWalletChargeFile.toPath() : " + savedFile.toPath());
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public String saveWalletRefund(WalletDto.Request.UpdateRefund request, MultipartFile file) {
        String filePath = FILE_REFUND_PATH + "images";
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("saveWalletRefund.toPath() : " + savedFile.toPath());
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public String saveMedia(MediaDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_MEDIA_PATH + "images";
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("saveMediaFile.toPath() : " + savedFile.toPath());
        file.transferTo(savedFile.toPath());
        if (uploadPath.startsWith(".")) {
            uploadPath = uploadPath.substring(1);
        }
        uploadPath = uploadPath.replace("\\", "/");
        return uploadPath + savedFile.getName();
    }

    @SneakyThrows
    @Override
    public String saveMediaPlacement(MediaPlacementDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_MEDIA_PLACEMENT_PATH + "images";
        String ymdPath = UpLoadFileUtils.calcPath(filePath);
        String uploadPath = filePath + ymdPath + "/";
        File savedFile = new File(String.format(
                "%s%s.%s",
                uploadPath, UUID.randomUUID(),
                FilenameUtils.getExtension(file.getOriginalFilename())));
        savedFile.getParentFile().mkdirs();

        System.out.println("saveMediaPlacementFile.toPath() : " + savedFile.toPath());
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
