package com.adplatform.restApi.infra.file.service;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.wallet.dto.WalletDto;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsFileService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final String FILE_PATH = "images/";
    private static final String FILE_PROOF_PATH = "proof/";
    private static final String FILE_COMPANY_PATH = "company/";
    private static final String FILE_MEDIA_PATH = "media/";
    private static final String FILE_MEDIA_PLACEMENT_PATH = "placement/";
    private static final String FILE_CHARGE_PATH = "charge/";
    private static final String FILE_REFUND_PATH = "refund/";

//    public String upload(MultipartFile file, String dirName) throws IOException {
//        String fileName = dirName + "/" + file.getOriginalFilename();
//
//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return s3Client.getUrl(bucket, fileName).toString();
//    }

    @SneakyThrows
    public String save(CreativeDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_PATH + request.getAdGroupId();
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveProofFile(Creative creative, MultipartFile file) {
        String filePath = FILE_PROOF_PATH + creative.getAdGroup().getId();
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveCompany(CompanyDto.Request.Save request, MultipartFile file, String fType) {
        String filePath = FILE_COMPANY_PATH + fType;
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveWalletCharge(WalletDto.Request.SaveCredit request, MultipartFile file) {
        String filePath = FILE_CHARGE_PATH + "images";
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveWalletRefund(WalletDto.Request.UpdateRefund request, MultipartFile file) {
        String filePath = FILE_REFUND_PATH + "images";
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveMedia(MediaDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_MEDIA_PATH + "images";
        return this.upload(file, filePath);
    }

    @SneakyThrows
    public String saveMediaPlacement(MediaPlacementDto.Request.Save request, MultipartFile file) {
        String filePath = FILE_MEDIA_PLACEMENT_PATH + "images";
        return this.upload(file, filePath);
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // AWS
    ///////////////////////////////////////////////////////////////////////////////////
    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            System.out.println("file delete.");
        }else {
            System.out.println("file not delete.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws  IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public byte[] download(String fileKey) throws IOException {
        byte[] content = null;
        final S3Object s3Object = amazonS3Client.getObject(bucket, fileKey);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch(final IOException ex) {
            throw new IOException("IO Error Message= " + ex.getMessage());
        }
        return content;
    }
}
