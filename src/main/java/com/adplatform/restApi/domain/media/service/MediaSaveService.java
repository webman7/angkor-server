package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.dao.category.MediaCategoryRepository;
import com.adplatform.restApi.domain.media.dao.category.mapper.MediaCategorySaveQueryMapper;
import com.adplatform.restApi.domain.media.domain.*;
import com.adplatform.restApi.domain.media.dao.MediaRepository;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.MediaMapper;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryDto;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryMapper;
import com.adplatform.restApi.domain.media.exception.MediaUpdateException;
import com.adplatform.restApi.domain.media.exception.CategoryNotFoundException;
import com.adplatform.restApi.global.config.security.service.Sha256Service;
import com.adplatform.restApi.global.util.RandomCodeGenerator;
import com.adplatform.restApi.infra.file.service.AwsFileService;
import com.adplatform.restApi.infra.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MediaSaveService {

    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;
    private final MediaCategoryRepository mediaCategoryRepository;
    private final MediaCategoryMapper mediaCategoryMapper;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final MediaCategorySaveQueryMapper mediaCategorySaveQueryMapper;
    private final FileService fileService;
    private final AwsFileService awsFileService;

    public void save(MediaDto.Request.Save request) {
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        Media media = this.mediaMapper.toEntity(request, company);
        if(request.getMediaFiles().size() > 0) {
            request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
        }
        this.mediaRepository.save(media);
    }

    public void update(MediaDto.Request.Update request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).update(request);
            if(request.getMediaFiles().size() > 0) {
                request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
            }
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    public void saveAdmin(MediaDto.Request.Save request) {
        Company company = CompanyFindUtils.findByIdOrElseThrow(request.getCompanyId(), this.companyRepository);
        Media media = this.mediaMapper.toEntity(request, company);

        if(request.getMediaFiles().size() > 0) {
            request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
        }
        Integer mediaId = this.mediaRepository.save(media).getId();
        //관리자 메모 업데이트
        MediaFindUtils.findByIdOrElseThrow(mediaId, this.mediaRepository).saveAdminMemo(request);

        // 루프 돌면서 인서트
        List<Category> category = this.findByCategoryId(request.getCategory());
        for (Category ca: category) {
            this.mediaCategorySaveQueryMapper.insertMediaCategory(mediaId, ca.getId());
        }
    }

    public void updateAdmin(MediaDto.Request.Update request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).update(request).updateAdminMemo(request);
            if(request.getMediaFiles().size() > 0) {
                request.getMediaFiles().forEach(file -> media.addMediaFile(this.saveMediaFile(request, media, file)));
            }

            // 매체 카테고리 삭제
            this.mediaCategorySaveQueryMapper.deleteMediaCategory(request.getId());

            // 루프 돌면서 인서트
            List<Category> category = this.findByCategoryId(request.getCategory());
            for (Category ca: category) {
                this.mediaCategorySaveQueryMapper.insertMediaCategory(media.getId(), ca.getId());
            }

        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    public void updateAdminApprove(MediaDto.Request.Confirm request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).updateAdminApprove(request);

            String appKey = new RandomCodeGenerator().generate(20);
            String appSecretTmp = new RandomCodeGenerator().generate(10);
            // appSecret = 임의 숫자 10개 + id + company_info_id + reg_date
            String appSecret = Sha256Service.SHA256(appSecretTmp + String.valueOf(media.getId()) + String.valueOf(media.getCompany().getId()) + String.valueOf(media.getCreatedAt()));
            MediaFindUtils.findByIdOrElseThrow(media.getId(), this.mediaRepository).updateAdminAppKey(appKey, appSecret);

            // 매체 카테고리 삭제
            this.mediaCategorySaveQueryMapper.deleteMediaCategory(request.getId());

            // 루프 돌면서 인서트
            List<Category> category = this.findByCategoryId(request.getCategory());
            for (Category ca: category) {
                this.mediaCategorySaveQueryMapper.insertMediaCategory(media.getId(), ca.getId());
            }
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    public void updateAdminReject(MediaDto.Request.Confirm request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).updateStatusAdminMemo(request);
            media.changeStatusR();
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    public void updateAdminDelete(MediaDto.Request.Confirm request) {
        try{
            Media media = MediaFindUtils.findByIdOrElseThrow(request.getId(), this.mediaRepository).updateStatusAdminMemo(request);
            media.changeStatusD();
        }catch (Exception e){
            throw new MediaUpdateException();
        }
    }

    private List<Category> findByCategoryId(List<Integer> categoryId) {
        return categoryId.stream().map(this::findByCategoryIdOrElseThrow).collect(Collectors.toList());
    }

    private Category findByCategoryIdOrElseThrow(Integer categoryId) {
        return this.categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public void delete(Integer id) {
        MediaFindUtils.findByIdOrElseThrow(id, this.mediaRepository).delete();
    }

    @SneakyThrows
    private MediaFile saveMediaFile(MediaDto.Request.Save request, Media media, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimetype = Files.probeContentType(Paths.get(originalFilename));
//        String savedFileUrl = this.fileService.saveMedia(request, file);
        String savedFileUrl = this.awsFileService.saveMedia(request, file);
        int index = savedFileUrl.lastIndexOf("/");
        String savedFilename = savedFileUrl.substring(index+1);
        return new MediaFile(media, this.mediaFileInformation(file, savedFileUrl, savedFilename, originalFilename, mimetype));
    }

    @SneakyThrows
    private FileInformation mediaFileInformation(MultipartFile file, String savedFileUrl, String savedFilename, String originalFilename, String mimetype) {
        FileInformation.FileType fileType;
        if (mimetype.startsWith("image")) {
            fileType = FileInformation.FileType.IMAGE;
        } else if (mimetype.startsWith("application/pdf")) {
            fileType = FileInformation.FileType.PDF;
        } else {
            throw new UnsupportedOperationException();
        }

        return FileInformation.builder()
                .url(savedFileUrl)
                .fileType(fileType)
                .fileSize(file.getSize())
                .filename(savedFilename)
                .originalFileName(originalFilename)
                .mimeType(mimetype)
                .build();
    }
}
